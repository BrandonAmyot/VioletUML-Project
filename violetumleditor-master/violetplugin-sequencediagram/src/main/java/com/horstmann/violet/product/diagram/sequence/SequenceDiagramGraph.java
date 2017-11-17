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

package com.horstmann.violet.product.diagram.sequence;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.common.edge.NoteEdge;
import com.horstmann.violet.product.diagram.common.node.NoteNode;
import com.horstmann.violet.product.diagram.sequence.edge.AsynchronousCallEdge;
import com.horstmann.violet.product.diagram.sequence.edge.SynchronousCallEdge;
import com.horstmann.violet.product.diagram.sequence.edge.ReturnEdge;
import com.horstmann.violet.product.diagram.sequence.node.ActivationBarNode;
import com.horstmann.violet.product.diagram.sequence.node.CombinedFragmentNode;
import com.horstmann.violet.product.diagram.sequence.node.LifelineNode;

/**
 * A UML sequence diagram.
 */
public class SequenceDiagramGraph extends AbstractGraph {
	// Variables used for check methods
	public static boolean areBarsEmpty = false;
	public static boolean shouldSuggestPattern = false;

	private boolean shouldCheckAgain = true;
	private boolean runOnce = false;

	@Override
	public boolean addNode(INode newNode, Point2D p) 
	{
		INode foundNode = findNode(p);
		if (foundNode == null && newNode.getClass().isAssignableFrom(ActivationBarNode.class)) 
		{
			return false;
		}

		return super.addNode(newNode, p);
	}

	// J
	// CHANGED*******************************************************************
	// Override draw() to automatically call check methods frequently
	@Override
	public void draw(Graphics2D graphics) 
	{
		super.draw(graphics);
		checkEmptyBars();
		checkGraspSuggestion();

//			if (runOnce) 
//			{
//				JFrame frame = new JFrame();
//				JOptionPane.showMessageDialog(frame,
//						"An obejct has too many outgoing message calls. To lower dependencies, use the controller pattern.",
//						"Message Calls!", JOptionPane.WARNING_MESSAGE);
//				
//				runOnce = false;
//			}


	}


	//J CHANGED*******************************************************************
    // Checks through entire graph to see if any activation bar has more than 5 calls ongoing
    public void checkGraspSuggestion()
    {
    	for(INode node : this.getAllNodes())
    	{
            if(node instanceof ActivationBarNode)
            {    			   			
                if(this.getAllEdges().size() > 0)
                {
                    int countEdges = 0;
                    for(IEdge edge : this.getAllEdges())
                    {
                        if(node == edge.getStartNode())
                        {
                        	countEdges++;
                        }
                    }
                    
                    if(countEdges >= 5)
                    {
                    	shouldSuggestPattern = true;
                    	
                    	//if(shouldCheckAgain)
//    						{
//    							runOnce =true;
//    							shouldCheckAgain = false;
//    						}
                    	
                    	return;
                    }
                    else
                    {
                    	shouldSuggestPattern = false;
                    
                    }
                    
                }

                else
                {
                	//shouldSuggestPattern = false;
                	shouldSuggestPattern = false;
                }
        	}   	    		
		}
    }
	

		// J
		// CHANGED*******************************************************************
		// Checks through entire graph to see if at least 1 activation bar has no
		// connecting edges (meaning it's empty)
		public void checkEmptyBars() 
		{
			for (INode node : this.getAllNodes()) 
			{
				if (node instanceof ActivationBarNode) 
				{
					if (this.getAllEdges().size() > 0) 
					{
						int countEdges = 0;
						for (IEdge edge : this.getAllEdges()) 
						{
							if (node != edge.getStartNode() && node != edge.getEndNode()) 
							{
								countEdges++;

								if (countEdges == this.getAllEdges().size()) 
								{
									areBarsEmpty = true;
									return;
								} 
								else 
								{
									areBarsEmpty = false;
								}
							} 
							else 
							{
								areBarsEmpty = false;
							}
						}
					}

					else 
					{
						areBarsEmpty = true;
						return;
					}
				}
			}
		}

		public List<INode> getNodePrototypes() 
		{
			return NODE_PROTOTYPES;
		}

		public List<IEdge> getEdgePrototypes() 
		{
			return EDGE_PROTOTYPES;
		}

		private static final List<INode> NODE_PROTOTYPES = new ArrayList<INode>(
				Arrays.asList(new LifelineNode(), new ActivationBarNode(), new CombinedFragmentNode(), new NoteNode()));

		private static final List<IEdge> EDGE_PROTOTYPES = new ArrayList<IEdge>(
				Arrays.asList(new SynchronousCallEdge(), new AsynchronousCallEdge(), new ReturnEdge(), new NoteEdge()));
	}