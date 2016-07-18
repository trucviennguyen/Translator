/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cimec.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <p>Title: MDC_MMAX</p>
 * <p>Description: Relation Extraction using Composite Kernel with Diverse Features</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Institution: Center for Mind/Brain Sciences (CIMeC)</p>
 * @author Truc-Vien T. Nguyen
 * @version 1.1
 */
public class SortedFile extends File {

    public SortedFile(String pathname) {
        super(pathname);
    } // SortedFile
    
    @Override
    public SortedFile[] listFiles() {
        File[] lst = super.listFiles();
        SortedFile[] newList = new SortedFile[lst.length];
        for (int i = 0; i < lst.length; i++) {
            String filename = lst[i].getAbsolutePath();
            int j = 0;
            while ( (j < i) && (filename.compareTo(newList[j].getAbsolutePath())) > 0)
                j++;
            if (j == i)
                newList[i] = new SortedFile(lst[i].getAbsolutePath());
            else {
                System.arraycopy(newList, j, newList, j + 1, i - j);
                newList[j] = new SortedFile(lst[i].getAbsolutePath());
            }
        }
        return newList;
    }
    
    @Override
    public SortedFile[] listFiles(FilenameFilter filter) {
        File[] lst = super.listFiles(filter);
        SortedFile[] newList = new SortedFile[lst.length];
        for (int i = 0; i < lst.length; i++) {
            String filename = lst[i].getAbsolutePath();
            int j = 0;
            while ( (j < i) && (filename.compareTo(newList[j].getAbsolutePath())) > 0)
                j++;
            if (j == i)
                newList[i] = new SortedFile(lst[i].getAbsolutePath());
            else {
                System.arraycopy(newList, j, newList, j + 1, i - j);
                newList[j] = new SortedFile(lst[i].getAbsolutePath());
            }
        }
        return newList;
    }

} // SortedFile