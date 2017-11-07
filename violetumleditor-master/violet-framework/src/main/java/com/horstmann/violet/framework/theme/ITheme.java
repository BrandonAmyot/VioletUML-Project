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

package com.horstmann.violet.framework.theme;

import java.awt.Color;
import java.awt.Font;

/**
 * GUI theme. It includes look and feel and colors
 * 
 * @author Alexandre de Pellegrin
 * 
 */
public interface ITheme
{

    /**
     * Method called to apply look and feel theme
     */
    public void activate();
    
    public abstract ThemeInfo getThemeInfo();

    public abstract Color getWhiteColor();

    public abstract Color getBlackColor();

    public abstract Color getGridColor();

    public abstract Color getBackgroundColor();

    public abstract Font getWelcomeBigFont();

    public abstract Font getWelcomeSmallFont();

    public abstract Color getWelcomeBackgroundStartColor();

    public abstract Color getWelcomeBackgroundEndColor();

    public abstract Color getWelcomeBigForegroundColor();

    public abstract Color getWelcomeBigRolloverForegroundColor();

    public abstract Font getMenubarFont();

    public abstract Color getMenubarBackgroundColor();

    public abstract Color getMenubarForegroundColor();

    public abstract Color getSidebarBackgroundStartColor();

    public abstract Color getSidebarBackgroundEndColor();

    public abstract Color getSidebarElementBackgroundColor();

    public abstract Color getSidebarElementTitleBackgroundStartColor();

    public abstract Color getSidebarElementTitleBackgroundEndColor();

    public abstract Color getSidebarElementForegroundColor();

    public abstract Color getSidebarElementTitleOverColor();

    public abstract Color getSidebarBorderColor();

    public abstract Color getStatusbarBackgroundColor();

    public abstract Color getStatusbarBorderColor();

    public abstract Color getToggleButtonSelectedColor();

    public abstract Color getToggleButtonSelectedBorderColor();

    public abstract Color getToggleButtonUnselectedColor();

    public abstract Font getToggleButtonFont();

    public abstract Color getRolloverButtonDefaultColor();

    public abstract Color getRolloverButtonRolloverColor();

    public abstract Color getRolloverButtonRolloverBorderColor();

    public interface WelcomePanelTheme
    {

    }

}