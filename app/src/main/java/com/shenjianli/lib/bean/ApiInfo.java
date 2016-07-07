package com.shenjianli.lib.bean;

public class ApiInfo {
	
	private ApiInfoBean apiInfo;

    public ApiInfoBean getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfoBean apiInfo) {
        this.apiInfo = apiInfo;
    }

    public class ApiInfoBean {
        
    	private String apiName;
        private String apiKey;
        
		public String getApiName() {
			return apiName;
		}
		public void setApiName(String apiName) {
			this.apiName = apiName;
		}
		public String getApiKey() {
			return apiKey;
		}
		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
    }
}
