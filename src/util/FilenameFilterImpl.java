/*
 * FilenameFilterImpl.java
 *
 * CIMeC WikiParser -- a parser for Wikipedia dump
 * Copyright (c) 2012 Truc-Vien T. Nguyen. All Rights Reserved.
 *
 * WikiParser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WikiParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, bug reports, fixes, contact:
 *    Truc-Vien T. Nguyen
 *    trucvien.nguyen@gmail.com
 *    http://sites.google.com/site/trucviennguyen/
 *
 */

package cimec.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Truc-Vien T. Nguyen
 */

public class FilenameFilterImpl implements FilenameFilter {

    /**
    * Construction method
    */
    public FilenameFilterImpl(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Tests if a specified file should be included in a file list.
     *
     * @param   dir    the directory in which the file was found.
     * @param   name   the name of the file.
     * @return  <code>true</code> if and only if the name should be
     * included in the file list; <code>false</code> otherwise.
     */
    public boolean accept(File dir, String name) {
        if (name.endsWith(suffix))
            return true;
        return false;
    }

    // The expected suffix
    String suffix;

}
