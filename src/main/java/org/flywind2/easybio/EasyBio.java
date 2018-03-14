/**
 * 
 */
package org.flywind2.easybio;

import java.util.ArrayList;
import java.util.List;

import picard.cmdline.PicardCommandLine;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class EasyBio extends PicardCommandLine{
    
    private final static String COMMAND_LINE_NAME = "java -jar EasyPipe-<version>.jar";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        final List <String> packageList = new ArrayList<String>();
        packageList.add("org.flywind2.easybio");
        
        
        System.exit(new EasyBio().instanceMain(args,
                packageList, COMMAND_LINE_NAME));
    }

}
