package com.horstmann.violet.product.diagram.abstracts.node;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

/**
 * Node size is automatic. However, by implementing this interface,
 * the user is able to add extra width and extra height to this diagram element 
 * 
 * @author Alexandre de Pellegrin
 *
 */
public interface IResizableNode
{
    
	
	void setWantedSize(Rectangle2D size);
	//void setWantedSize(Ellipse2D size);


    
    /**
     * Methods returns point which allow to change size of node
     *
     * @return point which allow to change size of node
     */
    Rectangle2D getResizablePoint();
    //Ellipse2D getResizablePointNew();

}