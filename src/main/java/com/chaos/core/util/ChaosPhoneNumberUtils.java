package com.chaos.core.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 使用google的公共号码解析库对电话号码进行处理
 *
 * @author huangdawei 2020/6/18
 */
public class ChaosPhoneNumberUtils {

    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();


    /**
     * 解析电话号码，返回解析结果，若号码错误，则返回null
     *
     * @param phoneNumber   电话号码
     * @param filterRegions 限定的国家代码code列表，只用电话号码在这些区域中进行匹配 {@link com.google.i18n.phonenumbers.CountryCodeToRegionCodeMap}
     * @return 解析结果，可以得到国家code，处理后的自然号码（移除其他信息，譬如空格，括号，+）
     */
    public static Phonenumber.PhoneNumber resolvePhoneNumber(String phoneNumber, List<String> filterRegions) {
        Assert.hasText(phoneNumber, "电话号码不能为空");
        if (CollectionUtils.isEmpty(filterRegions)) {
            filterRegions = Collections.singletonList("CN");
        }

        for (String filterRegion : filterRegions) {
            try {
                Phonenumber.PhoneNumber parse = phoneNumberUtil.parse(phoneNumber, filterRegion);
                boolean isValid = phoneNumberUtil.isValidNumber(parse);
                if (isValid) {
                    return parse;
                }
            } catch (NumberParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



}
