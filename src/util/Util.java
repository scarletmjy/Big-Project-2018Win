package util;

public class Util {
	//�ж��ַ����Ƿ��Ǵ�����
	public static boolean isNumber(String str) {
		for(int i=0;i<str.length();i++) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
