package com.nhlstenden.amazonsimulatie.services;

import java.util.HashSet;
import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.StorageUnit;

public class StorageUnitService extends Service<StorageUnit> {
	private Set<StorageUnit> changedStorageUnits;
	
	public StorageUnitService(MapState mapState) {
		super(mapState);

		changedStorageUnits = new HashSet<StorageUnit>();
	}

	@Override
	public Set<StorageUnit> tick(Set<StorageUnit> storageUnits) {
		changedStorageUnits.clear();

		for (StorageUnit storageUnit : storageUnits) {
			Robot robot = storageUnit.getRobot();

			if (robot == null)
				continue;

			storageUnit.setVertex(robot.getVertex());
			
			changedStorageUnits.add(storageUnit);
		}

		return storageUnits;
	}
}
