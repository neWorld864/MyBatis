package member.model.exception;

public class MemberException extends Exception {
	// 사용자 정의 예외
	public MemberException() {}
	public MemberException(String msg) {
		super(msg);
	}
	
}
