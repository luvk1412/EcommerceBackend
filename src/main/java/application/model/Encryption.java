package application.model;

import static application.model.Constants.*;

public class Encryption {
        public static String encrypt(String str){
            int code;
            String result = EMPTY;
            for (int i = 0; i < str.length(); i++) {
                code = Math.round((float) Math.random()*8+1);
                result += code + Integer.toHexString( ((int) str.charAt(i) ) ^ code )+HI_FUN;
            }
            return result.substring(0, result.lastIndexOf(HI_FUN));
        }

        public static String decrypt(String str){
            str = str.replace(HI_FUN, EMPTY);
            String result = "";
            for (int i = 0; i < str.length(); i+=3) {
                String hex =  str.substring(i+1, i+3);
                result += (char) (Integer.parseInt(hex, HEX_BASE) ^ (Integer.parseInt(String.valueOf(str.charAt(i)))));
            }
            return result;
        }
}
