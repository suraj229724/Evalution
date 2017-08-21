/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.framework;

/**
 *
 * @author Suraj
 */
public class GlobalConstants {

    /**
     * sets the value for array of ALLOWED_IP_RANGE={"127.0.0.1",
     * "10.1.2.1-10.1.2.254","10.1.3.1-10.1.3.254"}
     *
     */
    public static String[] ALLOWED_IP_RANGE = new String[]{"127.0.0.1", "10.1.2.1-10.1.2.254", "10.1.3.1-10.1.3.254"};
    public static String TAG_POINT_CUT = "POINTCUT";
    public static String TAG_SYSTEM = "SYSTEM";
    public static String TAG_ACCESS = "ACCESS";
    public static String TAG_SCHEDULER = "SCHEDULER";
}
