/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.framework.injection.resources;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleUtils
{

    /**
     * @param obj that we need to look for resource
     * @return the corresponding resource bundle. Ex : ClassDiagramGraph.class -> ClassDiagramGraphStrings.properties
     */
    public static ResourceBundle getStringsResourceBundleForObject(Object obj)
    {
        String fullClassName = obj.getClass().getName();
        String packageClassName = obj.getClass().getPackage().getName();
        String shortClassName = fullClassName.substring(packageClassName.length() + 1);
        return ResourceBundle.getBundle(ResourceBundleConstant.GENERIC_STRINGS.replaceFirst("%", shortClassName), Locale
                .getDefault());
    }

}
