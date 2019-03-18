package com.fanqiao.secondkill.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaocaimi.admin.pojo.vo.EntryVo;
import com.xiaocaimi.admin.pojo.vo.LoanApplyConditionVo;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

//@Log4j2
public class AESRSAUtil {

    /**
     * RAS公钥
     */
    //@Value("${rsa.PublicKey}")
    private static String publicKeyStr
            = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy7+ptuC9B8yLLWTYhqoY8cM2mwePWvDHj7ajuoM/SClhc9Cli/X2AnQ4SgJamxw39wJehH+9j4Dk+HZpuRYjldTv3HuqGdfcWrZXMl9EnbAmb68bdWcUxKKqHeUY2AbyXPRxDY5pGeer9HvLeqoych0q4ukKK9hzmeF5XDYe6iEv15GGwfUWv3BqmpscyN3sod8DB/km8fF0wHj01zyPVuwRw2/O8gv6M+Sl3RQemkuVN1na3EI9g7FbtsVI5cys7nC08vKoPmuXIOcTRQKEtcBtSnMUWKfyOLC6AdlhTZ96n9nwgXdI94NgI9aUE2B1qFmDSyoc2Itd6P5V5PAITQIDAQAB";

    /**
     * RAS私钥
     */
    //@Value("${rsa.PrivateKey}")
    private static String privateKeyStr
            ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDLv6m24L0HzIstZNiGqhjxwzabB49a8MePtqO6gz9IKWFz0KWL9fYCdDhKAlqbHDf3Al6Ef72PgOT4dmm5FiOV1O/ce6oZ19xatlcyX0SdsCZvrxt1ZxTEoqod5RjYBvJc9HENjmkZ56v0e8t6qjJyHSri6Qor2HOZ4XlcNh7qIS/XkYbB9Ra/cGqamxzI3eyh3wMH+Sbx8XTAePTXPI9W7BHDb87yC/oz5KXdFB6aS5U3WdrcQj2DsVu2xUjlzKzucLTy8qg+a5cg5xNFAoS1wG1KcxRYp/I4sLoB2WFNn3qf2fCBd0j3g2Aj1pQTYHWoWYNLKhzYi13o/lXk8AhNAgMBAAECggEBAL0q+vhXGkpWZ1XDDqGg5b35QAaZ80g8yxusN8FgP+3QtbEgo8MZnsnSe7yDCPqALZnBcl3Ud/v3cR8aWbhFTM68v+JWUqzh1K3II5caCovZUyZYTscm6IpUeFt/LBzN/lG9hgVPFle919IK6fW+dU2rtPit4wqoUVQqmlG50eQPNxOXDMq2u8bpo+VT3klEFE73eMbkVRfEFt7HFR/pKHtPTfHT/B9S7fR+V/LBgysiGG/Ctjeqf90obINyl3qPWGtfcaSS7OUKKM524wXH7sBot8bbajYlBioVYNfU7ej2aEdzc0BASwj/5lXJeDQGXhReEErBmhLvnp5Uv4SE6cECgYEA/+7UJJOvuB10pFdxyaMv941U/7h1CLV7/dR6qGYc75fiQgouLmWdkpnvoEIHDoofRaiW98kRWHBOjPpBXDZxwfmK0zf6GTNN3GvRGeo0yf4Haxalo1cbug+q09fGIN/GZOuDHbDhV/wINb46cA1r6MV3xuEo6u/IJvMJ9gHwUx0CgYEAy81VQ7Zw/z0OwixKq0fVJ3MT1f0Cz9MlU28eEXF9BhphD5B0Wr170PmZjcyFvYSYfXFbI9LFOWhjdgjz4E+NU0KmIyUdJb+FBL+hbA4HjDWCgb0+s3HpEG9JVFF9ajdYeuLCV3VyCQaTctbNoYQIQbubNOlCIXgZ+Yn6QchU0vECgYBosJEmxSLD9YiZEQwCpzYf1ThHwnRdM5+cnSXvbJW5jC+JRBDP+dES/tT+gQPR9r4ECIp4oU7vFkGLeXLy/XJbHnCjJeqwFERtPc0zBJLDbZ4rOABZaHB3E6GVNlZdbCwDbDMeGhSToQzarw3IeDUD6oKsX74VHDCLfi886l4cIQKBgBb5gs8rVfmwDGXfUFuWsXTNCENrTlHXX7HUKPOWc52og6CZjERecALS4HiXwcvnkrkxejDDpfAe0HeR8swqq6fjL49us6Z5vKkYYT4pJrirn5a4msTn5nov0Xhr51N+Ab94qKfrWXui/iRICs5WcAfY+lowoX/cZjE3WLrptoZBAoGACUDFaIa33aOot/tdCW/z6zESb6cotLTOWrwX3xXqgqgdFF/fKrKg3G0ovPf8qJkmc8qJit3mRzegvgbFV0TOkrVrwKJkt1HXSiZ60etco5DB6n+EEtwphahLVaXzgItdzG7/9mWN39EACrnCwsbzREr/UtigydxFOc49uDaDJUA=";

    //解密
    public static LoanApplyConditionVo decryptStr(EntryVo req) {
        if (req == null){
            return null;
        }
        try{
            PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
            // 公钥加密AES秘钥后的内容(Base64编码)，进行Base64解码
            byte[] publicEncrypt = RSAUtil.base642Byte(req.getEntryKey());
            // 用私钥解密,得到aesKey
            byte[] aesKeyStrBytes = RSAUtil.privateDecrypt(publicEncrypt, privateKey);
            // 解密后的aesKey
            String aesKeyStr2 = new String(aesKeyStrBytes);
            // 将Base64编码后的AES秘钥转换成SecretKey对象
            SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr2);
            // AES秘钥加密后的内容(Base64编码)，进行Base64解码
            byte[] encryptAES = AESUtil.base642Byte(req.getEntryData());
            // 用AES秘钥解密实际的内容
            byte[] decryptAES = AESUtil.decryptAES(encryptAES, aesKey);
            // 解密后的实际内容
            System.out.println("解密后的实际内容: " + new String(decryptAES));
            return parseObject(new String(decryptAES));
        }catch (Exception e){
            System.out.println("xxxxxxxxxxxxxxxxxxxxxx数据解密失败，错误信息[{}]" + e);
            return null;
        }
    }


    /**
     * 功能描述: 数据加密
     *
     * @Author:xiaojian
     * @Date: 2018/12/25 15:52
     * @since: v1.0
     */
    public static Map<String, Object> encryptStr(String message) {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            // 生成AES秘钥，并Base64编码
            String aesKeyStr = AESUtil.genKeyAES();
            //用公钥加密AES秘钥
            byte[] publicEncrypt = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), publicKey);
            //公钥加密AES秘钥后的内容Base64编码
            String publicEncryptStr = RSAUtil.byte2Base64(publicEncrypt);
            resultMap.put("entryKey", publicEncryptStr);
            //将Base64编码后的AES秘钥转换成SecretKey对象
            SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
            //用AES秘钥加密实际的内容
            byte[] encryptAES = AESUtil.encryptAES(message.getBytes(), aesKey);
            //AES秘钥加密后的内容Base64编码
            String encryptAESStr = AESUtil.byte2Base64(encryptAES);
            resultMap.put("entryData", encryptAESStr);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>数据加密成功！");
            return resultMap;
        } catch (Exception e){
            System.out.println("xxxxxxxxxxxxxxxxxxxxxx数据加密失败，错误信息[{}] " + e);
            return null;
        }
    }

    private static LoanApplyConditionVo parseObject(String token){
        try {
            return JSON.parseObject(token, LoanApplyConditionVo.class);
        }
        catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXX转成Object出现异常，异常信息[{}]" + e);
            return null;
        }
    }


    private static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws Exception {
        LoanApplyConditionVo loanApplyConditionVo = new LoanApplyConditionVo();
        String string1 = "2019-03-11 00:00:00";
        String string2 = "2019-03-18 23:59:59";

        loanApplyConditionVo.setApplyTimeStart(string1);
        loanApplyConditionVo.setApplyTimeEnd(string2);
        loanApplyConditionVo.setPage(1);
        loanApplyConditionVo.setSize(10);



        String paramPhoneString = objectMapper.writeValueAsString(loanApplyConditionVo);

        Map<String, Object> map = encryptStr(paramPhoneString);
    }

/*Map<String, String> paramPhoneMap = new HashMap<>();
        paramPhoneMap.put("applyTimeStart", string1);
        paramPhoneMap.put("applyTimeEnd", string2);
        paramPhoneMap.put("page", mobilePhone);
        paramPhoneMap.put("size", mobilePhone);*/


}
