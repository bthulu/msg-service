package bthulu.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gejian at 2018/8/21 12:58
 */
public class SmsResponse {
	private static final Logger log = LoggerFactory.getLogger(SmsResponse.class);
	private static final Map<Integer, String> RESPONSE_MAP = new HashMap(19);
	private static final SmsResponse OK;

	static {
		RESPONSE_MAP.put(0, "提交成功");
		RESPONSE_MAP.put(101, "无此用户");
		RESPONSE_MAP.put(102, "密码错误");
		RESPONSE_MAP.put(103, "提交过快（提交速度超过流速限制）");
		RESPONSE_MAP.put(104, "系统忙（因平台的原因，暂时无法处理提交的短信）");
		RESPONSE_MAP.put(105, "敏感短信（短信内容包含敏感词）");
		RESPONSE_MAP.put(106, "消息长度错误（>536或<=0）");
		RESPONSE_MAP.put(108, "手机号码个数错误（群发>50000或<=0;）");
		RESPONSE_MAP.put(109, "无发送额度（该用户可用短信数已使用完）");
		RESPONSE_MAP.put(110, "不在发送时间内");
		RESPONSE_MAP.put(111, "超出该账户当月发送额度限制");
		RESPONSE_MAP.put(112, "无此产品，用户没有订购该产品");
		RESPONSE_MAP.put(113, "extno格式错（非数字或者长度不对）");
		RESPONSE_MAP.put(115, "自动审核驳回");
		RESPONSE_MAP.put(116, "签名不合法，未带签名（用户必须带签名的前提下）");
		RESPONSE_MAP.put(117, "IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
		RESPONSE_MAP.put(118, "用户没有相应的发送权限");
		RESPONSE_MAP.put(119, "用户已过期");
		RESPONSE_MAP.put(120, "内容不在白名单中");
		OK = new SmsResponse(0, "提交成功");
	}

	private int stat;
	private String msg;

	private SmsResponse(int stat, String msg) {
		this.stat = stat;
		this.msg = msg;
	}

	static SmsResponse getResponse(int stat) {
		if (stat == 0) {
			return OK;
		} else {
			SmsResponse response = new SmsResponse(stat, (String) RESPONSE_MAP.get(stat));
			if (log.isWarnEnabled()) {
				log.warn(response.toString());
			}

			return response;
		}
	}

	public Integer getStat() {
		return this.stat;
	}

	public String getMsg() {
		return this.msg;
	}

	public String toString() {
		return "SmsResponse{stat=" + this.stat + ", msg='" + this.msg + '\'' + '}';
	}
}
