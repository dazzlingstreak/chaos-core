package com.chaos.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 楼层号排序，例如正常楼层数字、地下楼层带字母的
 * @author huangdawei 2021/5/12
 */
public class FloorSortUtil {


    /**
     * 获取字符串的首个非数字的下标
     *
     * @param str
     * @return
     */
    public static int getNongigitIndex(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return i;
        }
        return -1;
    }

    /**
     * 楼层号排序（quick sort）
     *
     * @param floorNames
     * @return
     */
    public static List<String> sortBuildingFloor(List<String> floorNames) {
        if (floorNames.size() < 2) {
            return floorNames;
        }
        List<String> newList = new ArrayList<>();
        //取随机位置的楼层号作为比较的基础，将所有数据拆成比它大的、比它小的2个组，每个组再进行组内排序
        int randomIndex = new Random().nextInt(floorNames.size());
        String pivot = floorNames.get(randomIndex);
        int pivotNonDigitIndex = getNongigitIndex(pivot);

        List<String> less = new ArrayList<>();
        List<String> greater = new ArrayList<>();

        for (int i = 0; i < floorNames.size(); i++) {
            if (i == randomIndex) {
                continue;
            }
            String item = floorNames.get(i);
            int nonDigitIndex = getNongigitIndex(item);
            if (nonDigitIndex < 0) {
                //item为纯数字
                Integer itemInteger = Integer.valueOf(item);
                if (pivotNonDigitIndex < 0) {
                    //标尺值是纯数字
                    Integer pivotInteger = Integer.valueOf(pivot);
                    if (itemInteger <= pivotInteger) {
                        less.add(item);
                    } else {
                        greater.add(item);
                    }
                } else if (pivotNonDigitIndex == 0) {
                    //标尺值首字母非数字
                    greater.add(item);
                } else {
                    //标尺值起始是数字，中间有非数字
                    String pivotString = pivot.substring(0, pivotNonDigitIndex);
                    Integer pivotInteger = Integer.valueOf(pivotString);
                    if (itemInteger <= pivotInteger) {
                        less.add(item);
                    } else {
                        greater.add(item);
                    }
                }
            } else if (nonDigitIndex == 0) {
                //item是首字母非数字
                //标尺值是纯数字
                if (pivotNonDigitIndex < 0) {
                    less.add(item);
                } else if (pivotNonDigitIndex == 0) {
                    //标尺值首字母非数字
                    if (item.compareTo(pivot) >= 0) {
                        less.add(item);
                    } else {
                        greater.add(item);
                    }
                } else {
                    //标尺值起始是数字，中间有非数字
                    less.add(item);
                }
            } else {
                //item起始是数字，中间有非数字
                String itemString = item.substring(0, nonDigitIndex);
                Integer itemInteger = Integer.valueOf(itemString);

                if (pivotNonDigitIndex < 0) {
                    Integer pivotInteger = Integer.valueOf(pivot);
                    if (itemInteger < pivotInteger) {
                        less.add(item);
                    } else {
                        greater.add(item);
                    }
                } else if (pivotNonDigitIndex == 0) {
                    greater.add(item);
                } else {
                    String pivotString = pivot.substring(0, pivotNonDigitIndex);
                    Integer pivotInteger = Integer.valueOf(pivotString);
                    if (itemInteger < pivotInteger) {
                        less.add(item);
                    } else if (itemInteger > pivotInteger) {
                        greater.add(item);
                    } else {
                        if (item.compareTo(pivot) > 0) {
                            greater.add(item);
                        } else {
                            less.add(item);
                        }
                    }
                }
            }


        }

        List<String> lessSort = sortBuildingFloor(less);
        List<String> greaterSort = sortBuildingFloor(greater);
        newList.addAll(lessSort);
        newList.add(pivot);
        newList.addAll(greaterSort);
        return newList;
    }

    public static void main(String[] args) {
        List<String> floorNames = Arrays.asList("5", "10", "3", "B1", "B2", "-3", "-1", "3F");
        List<String> sortFloorNames = sortBuildingFloor(floorNames);
        System.out.println(sortFloorNames);

    }

}
