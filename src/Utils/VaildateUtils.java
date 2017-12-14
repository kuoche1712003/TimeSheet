package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VaildateUtils {
		private VaildateUtils(){
			
		}
		/**
	     * <p>檢查字串是否為空格、空白字元或 null</p>
	     *
	     * <pre>
	     * ValidateUtils.isBlank(null)      = true
	     * ValidateUtils.isBlank("")        = true
	     * ValidateUtils.isBlank(" ")       = true
	     * ValidateUtils.isBlank("bob")     = false
	     * ValidateUtils.isBlank("  bob  ") = false
	     * </pre>
	     */
		public static final Pattern VALID_EMID_REGEX = 
	    	    Pattern.compile("[em]{2}+[0-9]{4}", Pattern.CASE_INSENSITIVE);

		public static boolean isEmid(String emid){
			 Matcher matcher = VALID_EMID_REGEX .matcher(emid);
 	        return matcher.find();
		}
		
		
	    public static boolean isBlank(String value) {
	        return value == null || value.trim().length() == 0;
	    }

	    /**
	     * <p>檢查字串是否為自然數(正整數)</p>
	     */
	    public static boolean isNaturalNumbers(String number) {
	        return number.matches("\\d*") && Integer.parseInt(number) > 0;
	    }

	    /**
	     * <p>檢查字串是否為正浮點數</p>
	     */
	    public static boolean isPositiveDouble(String number) {
	        return number.matches("\\d*|\\d*\\.\\d*") && Double.parseDouble(number) > 0;
	    }
		
	    
	    public static final Pattern VALID_PASSWD_REGEX=
	    	    Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})", Pattern.CASE_INSENSITIVE);

	    public static boolean isPasswd(String passwd){
	    	Matcher matcher = VALID_PASSWD_REGEX .matcher(passwd);
	        return matcher.find();
	    }
	    
	    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
	    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	    public static boolean isEmail(String email) {
	    	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
	    	        return matcher.find();
	    	}
	    /**
	     * <p>檢查字串是否為0~8</p>
	     */
	    public static boolean isNumber(String number){
	    	return number.matches("\\d*");
	    }
	    
	    public static final Pattern VALID_ID_ADDRESS_REGEX = Pattern.compile("^[A-Z]{1}[0-9]{9}$",Pattern.CASE_INSENSITIVE);
	    	
	    public static boolean isId(String id){
	    	Matcher matcher = VALID_ID_ADDRESS_REGEX.matcher(id);
	    	return matcher.find();
	    }
	    	
	    	
	    /**
	     * <p>檢查字串是否為0~8</p>
	     */
	    public static boolean isRightHr(String number){
	    	return number.matches("\\d*") && (Integer.parseInt(number) >= 0  &&  Integer.parseInt(number) <= 8  );
	    }
	    
	    /**
	     * <p>檢查字串是否為0~4</p>
	     */
	    public static boolean isRightOverHr(String number){
	    	return number.matches("\\d*") && (Integer.parseInt(number) >= 0  &&  Integer.parseInt(number) <= 4  );
	    }
//	    public static boolean isEmail(String email){
//	    	Pattern p = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
//	    	Matcher m = p.matcher(email);
//
//	    	if (m.find()){
//	    		return true;
//	    	}else {
//	    		return false;
//	    	}
//	    }
//	    public static boolean isRightEmid(String emid){
//	    	boolean answer;
//	    	
//	    	
//	    	
//	    	return answer;
//	    }
		
		
}
