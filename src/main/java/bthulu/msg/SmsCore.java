package bthulu.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author gejian at 2018/8/21 12:58
 */
public class SmsCore {
	private static final Logger log = LoggerFactory.getLogger(SmsCore.class);
	private SmsConfig smsConfig = new SmsConfig();

	public SmsCore() {
	}

	public void setSmsConfig(SmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}

	public SmsResponse getSms(String phoneNo, String sign, String msg) throws IOException {
		char[] chars = sign.toCharArray();
		if (chars[0] == 12304 && chars[chars.length - 1] == 12305) {
			String send = this.send(phoneNo, sign, msg, false);
			String[] split = send.split(",");
			return SmsResponse.getResponse(Integer.valueOf(split[1]));
		} else {
			throw new IllegalArgumentException("签名错误, 如无签名, 可传入默认值: 【安景软件】");
		}
	}

	private String send(String phoneNo, String sign, String msg, boolean needstatus) throws IOException {
		msg = URLEncoder.encode(sign + msg, "UTF-8");
		String requestURL =
				this.smsConfig.getUri() + "?" + this.smsConfig.getSecurity() + "&mobile=" + phoneNo + "&msg=" + msg
						+ "&needstatus=" + needstatus;
		if (log.isDebugEnabled()) {
			log.debug("短信请求报文: " + requestURL);
		}

		InputStream content = null;
		BufferedReader reader = null;

		String back;
		try {
			URL url = new URL(requestURL);
			URLConnection urlConnection = url.openConnection();
			content = (InputStream) urlConnection.getContent();
			reader = new BufferedReader(new InputStreamReader(content, "UTF-8"));
			back = reader.readLine();
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException var19) {
					log.error("短信请求InputStream关闭失败! ", var19);
				}
			}

			if (reader != null) {
				try {
					reader.close();
				} catch (IOException var18) {
					log.error("短信请求BufferedReader关闭失败! ", var18);
				}
			}

		}

		if (log.isTraceEnabled()) {
			log.trace("返回报文: " + back);
		}

		return back;
	}
}
