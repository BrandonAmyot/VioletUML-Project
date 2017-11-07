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

package com.horstmann.violet.eclipseplugin.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.horstmann.violet.product.diagram.state.StateDiagramGraph;

/**
 * State diagram wizard
 * 
 * @author Alexandre de Pellegrin
 * 
 */
public class NewStateDiagramWizard extends NewWizard
{

    public void init(IWorkbench workbench, IStructuredSelection selection)
    {
        super.init(workbench, selection);
        this.setPageTitle("New UML State Diagram");
        this.setUMLGraph(new StateDiagramGraph());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.horstmann.violet.eclipseplugin.wizards.NewWizard#getFileExtension()
     */
    public String getFileExtension()
    {
        return ".state.violet.html";
    }

}