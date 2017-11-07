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

package com.horstmann.violet.framework.file.persistence;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.horstmann.violet.product.diagram.property.ArrowheadChoiceList;
import com.horstmann.violet.product.diagram.property.BentStyleChoiceList;
import com.horstmann.violet.product.diagram.property.LineStyleChoiceList;
import com.horstmann.violet.framework.util.StringFilterOutputStream;
import com.horstmann.violet.product.diagram.common.node.DiagramLinkNode;
import com.horstmann.violet.product.diagram.common.edge.NoteEdge;
import com.horstmann.violet.product.diagram.common.node.NoteNode;
import com.horstmann.violet.product.diagram.common.node.PointNode;

/**
 * This class provides file format services
 * 
 * @author Alexandre de Pellegrin
 * 
 */
public class Violet016BackportFormatService
{

    /**
     * This filter guarantees compatibility for Violet 0.16 file format
     * 
     * @param in raw input stream
     * @return converted input stream
     */
    public static InputStream convertFromViolet016(InputStream in)
    {

        Map<String, String> replaceMap = new Hashtable<String, String>();
        replaceMap.putAll(violet016CompatibilityMap);

        // fix framework elements
        replaceMap.put("com.horstmann.violet.BentStyleChoiceList", BentStyleChoiceList.class.getName());
        replaceMap.put("com.horstmann.violet.LineStyleChoiceList", LineStyleChoiceList.class.getName());
        replaceMap.put("com.horstmann.violet.ArrowheadChoiceList", ArrowheadChoiceList.class.getName());

        // fix common elements package
        replaceMap.put("com.horstmann.violet.DiagramLinkNode", DiagramLinkNode.class.getName());
        replaceMap.put("com.horstmann.violet.NoteEdge", NoteEdge.class.getName());
        replaceMap.put("com.horstmann.violet.NoteNode", NoteNode.class.getName());
        replaceMap.put("com.horstmann.violet.PointNode", PointNode.class.getName());

        String original = getInputStreamContent(in);
        String replaced = replaceAll(original, replaceMap);
        try
        {
            return new ByteArrayInputStream(replaced.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            return new ByteArrayInputStream(replaced.getBytes());
        }
    }

    /**
     * Converts inputStream to String
     * 
     * @param in stream
     * @return string
     */
    private static String getInputStreamContent(InputStream in)
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            StringBuffer buffer = new StringBuffer();
            int len = 1024;
            char buf[] = new char[len];
            int numRead;
            while ((numRead = isr.read(buf, 0, len)) != -1)
            {
                buffer.append(buf, 0, numRead);
            }
            isr.close();
            return buffer.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters a string and replaces all key courrences issed from the map by its valye
     * 
     * @param input string
     * @param replaceMap key = searchedString / value = replaceString
     * @return filtered string
     */
    private static String replaceAll(String input, Map<String, String> replaceMap)
    {
        Set<String> set = replaceMap.keySet();
        for (Iterator<String> iter = set.iterator(); iter.hasNext();)
        {
            String searchedStr = iter.next();
            String replaceStr = replaceMap.get(searchedStr);
            input = input.replaceAll(searchedStr, replaceStr);
        }
        return input;
    }

    /**
     * This filter guarantees compatibility for Violet 0.16 file format
     * 
     * @param out raw output stream
     * @return converted output stream
     */
    public static OutputStream convertToViolet016(OutputStream out)
    {

        Map<String, String> replaceMap = new Hashtable<String, String>();
        replaceMap.putAll(getReversedMap(violet016CompatibilityMap));

        // fix framework elements
        replaceMap.put(BentStyleChoiceList.class.getName(), "com.horstmann.violet.BentStyleChoiceList");
        replaceMap.put(LineStyleChoiceList.class.getName(), "com.horstmann.violet.LineStyleChoiceList");
        replaceMap.put(ArrowheadChoiceList.class.getName(), "com.horstmann.violet.ArrowheadChoiceList");
        // fix common elements package
        replaceMap.put(DiagramLinkNode.class.getName(), "com.horstmann.violet.DiagramLinkNode");
        replaceMap.put(NoteEdge.class.getName(), "com.horstmann.violet.NoteEdge");
        replaceMap.put(NoteNode.class.getName(), "com.horstmann.violet.NoteNode");
        replaceMap.put(PointNode.class.getName(), "com.horstmann.violet.PointNode");

        StringFilterOutputStream filteredOutputStream = new StringFilterOutputStream(out, replaceMap);
        return filteredOutputStream;

    }

    /**
     * Registers new keys/values to keep Violet 0.16 compatibility. Keys are Violet 0.16 strings. Map values are real values
     * corresponding to their old Violet 0.16 string.
     * 
     * @param entries
     */
    public static void addViolet016CompatibilityEntries(Map<String, String> entries)
    {
        violet016CompatibilityMap.putAll(entries);
    }

    /**
     * Reverse a map. Be carefull to have unique values as they will become keys!
     * 
     * @param mapToReverse
     * @return
     */
    private static Map<String, String> getReversedMap(Map<String, String> mapToReverse)
    {
        Map<String, String> result = new HashMap<String, String>();
        for (String aKey : mapToReverse.keySet())
        {
            String aValue = mapToReverse.get(aKey);
            if (aValue != null && !result.containsKey(aValue))
            {
                result.put(aValue, aKey);
            }
        }
        return result;
    }

    /**
     * Violet 0.16 compatibility map . Keys are Violet 0.16 strings. Map values are real values corresponding to their old Violet
     * 0.16 text.
     */
    private static Map<String, String> violet016CompatibilityMap = new HashMap<String, String>();

}
