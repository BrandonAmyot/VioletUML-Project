package com.horstmann.violet.product.diagram.classes.edge;

/**
 * TODO javadoc
 * This ...
 *
 * @author Adrian Bobrowski <adrian071993@gmail.com>
 * @date 23.02.2016
 */
public class InheritanceEdgeBeanInfo extends ClassRelationshipEdgeBeanInfo
{
    protected InheritanceEdgeBeanInfo(Class<?> beanClass)
    {
        super(beanClass);
        displayStartArrowhead = false;
    }

    public InheritanceEdgeBeanInfo()
    {
        this(InheritanceEdge.class);
    }
}