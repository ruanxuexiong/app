package com.spx.coordinatorlayoutexample.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Android屏幕适配Dimens文件生成工具类
 * 参考:http://blog.csdn.net/guozhaohui628/article/details/71870530
 * http://blog.csdn.net/lmj623565791/article/details/45460089
 *
 * @author LTP 2017年8月15日
 */
public class AndroidDimensUtil {

    /**
     * 基准宽度和高度(通常设置成UI图的分辨率的高度和宽度)
     */
    private static final int baseHeight = 1920;
    private static final int baseWidth = 1080;

    /**
     * 生成Dimens文件的路径（项目的res文件夹）
     */
    private static String FILE_PATH;

    private static final String WidthTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private static final String HeightTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    public static void main(String[] args) {
        // 获取项目res文件的路径
        getResPath(new File("").getAbsolutePath());

        AndroidDimensUtil.createDimens(480, 320);
        AndroidDimensUtil.createDimens(800, 480);
        AndroidDimensUtil.createDimens(800, 600);
        AndroidDimensUtil.createDimens(854, 480);
        AndroidDimensUtil.createDimens(854, 540);
        AndroidDimensUtil.createDimens(960, 540);
        AndroidDimensUtil.createDimens(960, 640);
        AndroidDimensUtil.createDimens(1024, 600);
        AndroidDimensUtil.createDimens(1024, 768);
        AndroidDimensUtil.createDimens(1184, 720);
        AndroidDimensUtil.createDimens(1196, 720);
        AndroidDimensUtil.createDimens(1208, 720);
        AndroidDimensUtil.createDimens(1280, 720);
        AndroidDimensUtil.createDimens(1280, 768);
        AndroidDimensUtil.createDimens(1280, 800);
        AndroidDimensUtil.createDimens(1334, 750);
        AndroidDimensUtil.createDimens(1440, 900);
        AndroidDimensUtil.createDimens(1700, 1080);
        AndroidDimensUtil.createDimens(1776, 1080);
        AndroidDimensUtil.createDimens(1794, 1080);
        AndroidDimensUtil.createDimens(1800, 1080);
        AndroidDimensUtil.createDimens(1812, 1080);
        AndroidDimensUtil.createDimens(1920, 1080);
        AndroidDimensUtil.createDimens(1920, 1200);
        AndroidDimensUtil.createDimens(2160, 1080);

        AndroidDimensUtil.createDimens(2560, 1440);
        AndroidDimensUtil.createDimens(2560, 1600);
        AndroidDimensUtil.createDimens(2240, 1080);
        // 添加以上分辨率都木有通用的,参考:http://blog.csdn.net/guozhaohui628/article/details/71870530
//        AndroidDimensUtil.createCommonDimens(2);
    }

    /**
     * 生成相应分辨率的文件
     *
     * @param dimenHeight 要生成相应分辨率的高
     * @param dimenWidth  要生成相应分辨率的宽
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createDimens(int dimenHeight, int dimenWidth) {
        // 生成Height
        StringBuilder sbForHeight = new StringBuilder();
        // 生成头
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        // 计算倍数
        float scaleHeight = dimenHeight * 1.0f / baseHeight;
//        System.out.println("生成Height : " + dimenHeight + " , 基准Height : " + baseHeight + " , height比例 : " + scaleHeight);
        for (int i = 1; i < baseHeight; i++) {
            // 根据倍率（最终保留两位小数）生成
            sbForHeight.append(HeightTemplate.replace("{0}", i + "").replace("{1}", leftTwoDecimal(scaleHeight * i) + ""));
        }
        // 最后一个直接写成相应的高，不用计算
        sbForHeight.append(HeightTemplate.replace("{0}", baseHeight + "").replace("{1}", dimenHeight + ""));
        sbForHeight.append("</resources>");

        // 生成Width
        StringBuilder sbForWidth = new StringBuilder();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");

        float scaleWidth = dimenWidth * 1.0f / baseWidth;
//        System.out.println("生成Width : " + dimenWidth + " , 基准Width : " + baseWidth + " , width比例 : " + scaleWidth);

        for (int i = 1; i < baseWidth; i++) {
            sbForWidth.append(WidthTemplate.replace("{0}", i + "").replace("{1}", leftTwoDecimal(scaleWidth * i) + ""));
        }
        sbForWidth.append(WidthTemplate.replace("{0}", baseWidth + "").replace("{1}", dimenWidth + ""));
        sbForWidth.append("</resources>");

        // 生成文件
        File dimenFile = new File(FILE_PATH + File.separator + "values-" + dimenHeight + "x" + dimenWidth);
        dimenFile.mkdirs();
        System.out.println("指定分辨率:" + dimenHeight + "x" + dimenWidth);

        File lay_xFile = new File(dimenFile.getAbsolutePath(), "lay_x.xml");
        File lay_yFile = new File(dimenFile.getAbsolutePath(), "lay_y.xml");

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(lay_xFile));
            printWriter.print(sbForWidth.toString());
            printWriter.close();

            printWriter = new PrintWriter(new FileOutputStream(lay_yFile));
            printWriter.print(sbForHeight.toString());
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成适配未找到对应分辨率设备的通用dimen文件（直接放置在values中，单位为px）
     *
     * @param commonDensity 通用density的值
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createCommonDimens(int commonDensity) {
        float commonScale = 1.0f / commonDensity;

        // 生成Height
        StringBuilder sbForHeight = new StringBuilder();
        // 生成头
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        for (int i = 1; i < baseHeight + 1; i++) {
            // 根据倍率（最终保留两位小数）生成
            sbForHeight.append(HeightTemplate.replace("{0}", i + "").replace("{1}", leftTwoDecimal(commonScale * i) + "").replace("px", "dp"));
        }
        sbForHeight.append("</resources>");

        // 生成Width
        StringBuilder sbForWidth = new StringBuilder();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");

        for (int i = 1; i < baseWidth + 1; i++) {
            sbForWidth.append(WidthTemplate.replace("{0}", i + "").replace("{1}", leftTwoDecimal(commonScale * i) + "").replace("px", "dp"));
        }
        sbForWidth.append("</resources>");

        // 生成文件
        File dimenFile = new File(FILE_PATH + File.separator + "values");
        dimenFile.mkdirs();
        System.out.println("未指定的通用分辨率（values中）");

        File lay_xFile = new File(dimenFile.getAbsolutePath(), "lay_x.xml");
        File lay_yFile = new File(dimenFile.getAbsolutePath(), "lay_y.xml");

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(lay_xFile));
            printWriter.print(sbForWidth.toString());
            printWriter.close();

            printWriter = new PrintWriter(new FileOutputStream(lay_yFile));
            printWriter.print(sbForHeight.toString());
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保留两位小数
     *
     * @param a 要保留的Float数值
     * @return 保留后的数值
     */
    private static float leftTwoDecimal(float a) {
        return (int) (a * 100) / 100f;
    }

    /**
     * 利用递归查询res文件夹的绝对路径并赋值
     *
     * @param filePath 文件路径
     */
    private static void getResPath(String filePath) {
        for (File file : new File(filePath).listFiles()) {
            if (file.isDirectory()) {
                // 递归
                //这里将列出所有的文件夹
                if (file.getAbsolutePath().contains("src") && file.getAbsolutePath().contains("res")) {
                    System.out.println("res路径：" + file.getAbsolutePath());
                    FILE_PATH = file.getAbsolutePath();
                    return;
                }
                getResPath(file.getAbsolutePath());
            }
        }
    }
}
