/*
 *  RecessedBorder.java
 *  (ScissLib)
 *
 *  Copyright (c) 2004-2016 Hanns Holger Rutz. All rights reserved.
 *
 *	This library is free software; you can redistribute it and/or
 *	modify it under the terms of the GNU Lesser General Public
 *	License as published by the Free Software Foundation; either
 *	version 2.1 of the License, or (at your option) any later version.
 *
 *	This library is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *	Lesser General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public
 *	License along with this library; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 *	For further information, please contact Hanns Holger Rutz at
 *	contact@sciss.de
 */

package de.sciss.gui;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.*;

/** Not any longer "recessed", just round. */
public class RecessedBorder
        extends AbstractBorder {

    private static final int diameter	= 6;
    private final Insets	insets		= new Insets(3, 3, 4, 4);   // "historically"

//    private static final Color colrDark     = new Color(0x00, 0x00, 0x00, 0x88);
//    private static final Color colrLight    = new Color(0xFF, 0xFF, 0xFF, 0xD8);
//    private static final Stroke strkOutline = new BasicStroke(1.0f);
//    private static final Stroke strkInline  = new BasicStroke(2.0f);

    private Color	colrBg		= Color.black;

    private Shape	shpBg;
//    private Shape   shpInline;
//    private Shape   shpOutline;

    private int		recentWidth		= -1;
    private int		recentHeight	= -1;

    public RecessedBorder() {
        super();
    }

    public RecessedBorder(Color c) {
        this();
        setColor(c);
    }

    public void setColor(Color c) {
        colrBg = c;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(insets.top, insets.left, insets.bottom, insets.right);
    }

    public Insets getBorderInsets(Component c, Insets i) {
        i.top       = insets.top;
        i.left      = insets.left;
        i.bottom    = insets.bottom;
        i.right     = insets.right;
        return i;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        final Graphics2D		g2			= (Graphics2D) g;
        final AffineTransform	atOrig		= g2.getTransform();

        g2.translate(x, y);

        if ((width != recentWidth) || (height != recentHeight)) {
            // final RectangularShape r  = new RoundRectangle2D.Float(1.0f, 0.5f, width - 2.0f, height - 1.5f, diameter, diameter);
            final RectangularShape r  = new RoundRectangle2D.Float(0.0f, 0.0f, width, height, diameter, diameter);
            // final RectangularShape r2 = new RoundRectangle2D.Float(0.5f, 0.0f, width - 1.5f, height - 1.0f, diameter, diameter);
            final Area a = new Area(r);
            a.subtract(new Area(new Rectangle2D.Float(insets.left, insets.top,
                    width - insets.left - insets.right, height - insets.top - insets.bottom)));

//            shpOutline  = strkOutline.createStrokedShape(r2);
//            shpInline   = strkInline .createStrokedShape(r2);
            shpBg = a;

            recentWidth = width;
            recentHeight = height;
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setPaint(colrDark);
//        g2.fill(shpOutline);
//        g2.translate(1, 1);
//        g2.setPaint(colrLight);
//        g2.fill(shpInline);
//        g2.translate(-1, -1);
        g2.setPaint(colrBg);
        g2.fill(shpBg);

        g2.setTransform(atOrig);
    }
}