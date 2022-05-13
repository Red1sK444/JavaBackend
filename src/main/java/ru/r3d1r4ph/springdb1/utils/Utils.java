package ru.r3d1r4ph.springdb1.utils;

import com.opencsv.bean.CsvToBeanBuilder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static String getHashedPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] bytes = factory.generateSecret(spec).getEncoded();

            var sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }
    }

    public static <C> List<C> parseToCsv(String resPath, Class<C> type) throws NullPointerException {
        try (var csvStream = resPath.contains(":") ? new FileInputStream(resPath) : Utils.class.getResourceAsStream(resPath)) {
            return new CsvToBeanBuilder<C>(new InputStreamReader(Objects.requireNonNull(csvStream)))
                    .withSeparator(',')
                    .withType(type)
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static Date convertStringToDate(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
