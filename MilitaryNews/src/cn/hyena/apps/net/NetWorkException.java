package cn.hyena.apps.net;

/**
 * 褰撶绾跨姸鎬佹椂锛屽啀鑱旂綉璇锋眰鏁版嵁鏃讹紝鎶涘嚭姝ゅ紓锟?
 *  @author 
 */
public class NetWorkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NetWorkException() {
		super();
	}

	public NetWorkException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NetWorkException(String detailMessage) {
		super(detailMessage);
	}

	public NetWorkException(Throwable throwable) {
		super(throwable);
	}

}
