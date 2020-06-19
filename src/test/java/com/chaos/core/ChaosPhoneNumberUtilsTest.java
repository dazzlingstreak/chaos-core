package com.chaos.core;

import com.chaos.core.util.ChaosPhoneNumberUtils;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author huangdawei 2020/6/18
 */
@SpringBootTest
public class ChaosPhoneNumberUtilsTest {


    @Test
    public void resolvePhoneNumber() {

        List<String> regions = Arrays.asList("CN", "HK", "MO", "TW");

        Phonenumber.PhoneNumber phoneNumber1 = ChaosPhoneNumberUtils.resolvePhoneNumber("15298818995", regions);
        Phonenumber.PhoneNumber phoneNumber2 = ChaosPhoneNumberUtils.resolvePhoneNumber("18667048855", regions);
        Phonenumber.PhoneNumber phoneNumber3 = ChaosPhoneNumberUtils.resolvePhoneNumber("2929 2222", regions); //HK
        Phonenumber.PhoneNumber phoneNumber4 = ChaosPhoneNumberUtils.resolvePhoneNumber("(02)2348-2999", regions); //TW

        System.out.println("phoneNumber1:" + phoneNumber1);

        System.out.println("phoneNumber2:" + phoneNumber2);

        System.out.println("phoneNumber3:" + phoneNumber3);

        System.out.println("phoneNumber4:" + phoneNumber4);

    }


    @Test
    public void testMatch() throws NumberParseException {
        StringBuilder numbers = new StringBuilder();

        numbers.append("hello,152-9881-8995-dd");
        numbers.append("(02)2348-2999-dd");
        numbers.append("2929 2222");

//        for (int i = 0; i < 100; i++) {
//            numbers.append("My info: 415-666-7777,sss");
//        }

        // Matches all 100. Max only applies to failed cases.
        List<Phonenumber.PhoneNumber> expected = new ArrayList<Phonenumber.PhoneNumber>(100);
        Phonenumber.PhoneNumber number = PhoneNumberUtil.getInstance().parse("+14156667777", null);
        for (int i = 0; i < 100; i++) {
            expected.add(number);
        }


        Iterable<PhoneNumberMatch> iterable =
                PhoneNumberUtil.getInstance().findNumbers(numbers.toString(), "CN", PhoneNumberUtil.Leniency.VALID, 10);
        List<Phonenumber.PhoneNumber> actual = new ArrayList<Phonenumber.PhoneNumber>(100);
        for (PhoneNumberMatch match : iterable) {
            actual.add(match.number());
        }

        Assert.assertEquals(actual,expected);

    }

}
