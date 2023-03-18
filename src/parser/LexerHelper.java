package parser;

public class LexerHelper {
	
	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1; // DA IGUAL QUE DEVOLVER AQUI, VA A SER UN ERROR ASI QUE NO IMPORTA
	}

    public static double lexemeToReal(String str) {
        try {
            return Double.parseDouble(str);
        }
        catch(NumberFormatException e) {
            System.out.println(e);
        }
        return -1;
    }

    public static char lexemeToChar(String str) {
        try {
            char[] chars = str.toCharArray();
            char ch = 0;
            if(chars[0]=='\'' && chars[chars.length-1]=='\''){
                String str2=str.substring(1, str.length()-1);
                switch(str2){
                    case "\\n":
                        return '\n';
                    case "\\t":
                        return '\t';
                    case "\\r":
                        return '\r';
                    default:
                        if(str2.length()==1)
                            return str2.charAt(0);
                        else{
                            int i=Integer.parseInt(str2.substring(1,str2.length()));
                            char c= (char) i;
                            return c;
                        }
                }
            }
            return ch;
        }
        catch(NumberFormatException e) {
            System.out.println(e);
        }
        return 'a';
    }

}
