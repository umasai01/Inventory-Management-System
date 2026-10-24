package com.training.business.response;

import java.util.List;

import com.training.business.bean.MaterialTypeBean;
import com.training.business.bean.UnitBean;

// Response wrapper for dependent dropdown API: sends unit list + material type list together.
		public class UnitAndTypeResponse {
		private List<UnitBean> unitList;
		private List<MaterialTypeBean> materialTypeList;

		public UnitAndTypeResponse() {
			super();
		}

		public UnitAndTypeResponse(List<UnitBean> unitList, List<MaterialTypeBean> materialTypeList) {
			this.unitList = unitList;
			this.materialTypeList = materialTypeList;
		}

		// Getter used in JSON response serialization.
		public List<UnitBean> getUnitList() {
			return unitList;
		}

		// Getter used in JSON response serialization.
		public List<MaterialTypeBean> getMaterialTypeList() {
			return materialTypeList;
		}
    }
