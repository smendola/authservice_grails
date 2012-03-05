package com.pht.service

class DataTransferObject {
	DataTransferObject(Object that) {
		for (prop in that.metaClass.properties) {

			// Filter these special properties
			if (prop.name in ['class', 'count', 'properties', 'metaClass']) continue;

			// Copy similarly named properties from that to this
			if (this.metaClass.hasProperty(this, prop.name)) {
				this[prop.name] = that[prop.name]
			}
		}
	}
}
