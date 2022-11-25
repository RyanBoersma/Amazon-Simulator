package com.nhlstenden.amazonsimulatie.services;

import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.models.Model;

public abstract class Service<TModel extends Model> {
	protected MapState mapState;

	public Service(MapState mapState) {
		this.mapState = mapState;
	}

	/**
	 * Executes a service
	 * @param models to execute service on
	 * @return Set of all changed models
	 */
	public abstract Set<TModel> tick(Set<TModel> models);
}
