package com.nhlstenden.amazonsimulatie.base;

public class ClientCommand {
	private String command = "";

	/**
	 * Sets the command content
	 * @param command content
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Gets the command content
	 * @return content
	 */
	public String getCommand() {
		return this.command;
	}
}
