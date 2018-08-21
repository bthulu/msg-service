package bthulu.msg;

/**
 * @author gejian at 2018/8/21 12:58
 */
public class SmsConfig {
	private String uri = "http://120.26.66.24/msg/HttpBatchSendSM";
	private String security = "account=szajxx_hy&pswd=szajxx_hy123&product=109402988";

	public SmsConfig() {
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSecurity() {
		return this.security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}
}
