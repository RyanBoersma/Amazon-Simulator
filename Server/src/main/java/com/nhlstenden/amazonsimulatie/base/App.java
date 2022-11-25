package com.nhlstenden.amazonsimulatie.base;

import java.io.IOException;

import com.google.gson.Gson;
import com.nhlstenden.amazonsimulatie.controllers.SimulationController;
import com.nhlstenden.amazonsimulatie.views.DefaultWebSocketView;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
@EnableScheduling
public class App extends SpringBootServletInitializer implements WebSocketConfigurer {
	private static final int TARGET_TICK_RATE = 4;
	private static final int TARGET_TICK_DELAY = 1000/TARGET_TICK_RATE;

	private long currentTime = 0;

	private final SimulationController simulationController;

	public App() {
		this.simulationController = new SimulationController(TARGET_TICK_RATE);
		this.simulationController.start();
		
		this.currentTime = System.currentTimeMillis();
	}

	@Scheduled(fixedRate=TARGET_TICK_DELAY)
	private void Update() {
		long newTime = System.currentTimeMillis();
		long deltaTime = newTime - currentTime;
		currentTime = newTime;
		
		if (deltaTime < TARGET_TICK_DELAY * 0.75f)
			return;

		if (deltaTime > TARGET_TICK_DELAY * 1.5f)
			return;

		simulationController.tick();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new DefaultWebSocketHandler(simulationController), "/connectToSimulation");
	}

	private class DefaultWebSocketHandler extends TextWebSocketHandler {
		private final SimulationController simulationController;
		private DefaultWebSocketView simulationControllerWebsocketView;

		public DefaultWebSocketHandler(SimulationController simulationController) {
			this.simulationController = simulationController;
		}

		@Override
		public void afterConnectionEstablished(WebSocketSession session) {
			this.simulationControllerWebsocketView = new DefaultWebSocketView(session, simulationController);
		}

		@Override
		public void handleTextMessage(WebSocketSession session, TextMessage message) {
			Gson gson = new Gson();
			ClientCommand command = gson.fromJson(message.getPayload(), ClientCommand.class);

			simulationControllerWebsocketView.executeClientCommand(command);
		}

		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
			simulationControllerWebsocketView.close();
			session.close(CloseStatus.SERVER_ERROR);
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
			simulationControllerWebsocketView.close();
		}
	}
}