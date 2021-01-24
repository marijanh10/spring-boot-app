package hranj.marijan.springbootapp.utils;

import java.util.Random;

public final class OtpGenerator {

    private OtpGenerator() {}

    public static char[] generateOTP(int otpLength) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[otpLength];

        for(int i = 0; i < otpLength ; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

}
