package com.horstmann.violet.product.diagram.object.edge;

import com.horstmann.violet.product.diagram.abstracts.edge.bentstyle.BentStyle;
import com.horstmann.violet.product.diagram.common.edge.LabeledLineEdge;
import com.horstmann.violet.product.diagram.object.ObjectDiagramConstant;
import com.horstmann.violet.product.diagram.property.ArrowheadChoiceList;
import com.horstmann.violet.product.diagram.property.LineStyleChoiceList;

/**
 * TODO javadoc
 * This ...
 *
 * @author Adrian Bobrowski <adrian071993@gmail.com>
 * @date 23.02.2016
 */
public class AssociationEdge extends LabeledLineEdge
{
    public AssociationEdge()
    {
        super();
        setBentStyle(BentStyle.STRAIGHT);
        setStartArrowhead(ArrowheadChoiceList.NONE);
        setEndArrowhead(ArrowheadChoiceList.V);
    }

    protected AssociationEdge(AssociationEdge cloned)
    {
        super(cloned);
        setBentStyle(BentStyle.STRAIGHT);
        setStartArrowhead(ArrowheadChoiceList.NONE);
        setEndArrowhead(ArrowheadChoiceList.V);
    }

    @Override
    protected AssociationEdge copy() throws CloneNotSupportedException
    {
        return new AssociationEdge(this);
    }

    @Override
    protected void beforeReconstruction()
    {
        super.beforeReconstruction();
        setBentStyle(BentStyle.STRAIGHT);
        setStartArrowhead(ArrowheadChoiceList.NONE);
        setEndArrowhead(ArrowheadChoiceList.V);
    }


    @Override
    public String getToolTip()
    {
        return ObjectDiagramConstant.OBJECT_DIAGRAM_RESOURCE.getString("tooltip.association_edge");
    }
}
