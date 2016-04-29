/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pepsoft.worldpainter.heightMaps.gui;

import org.pepsoft.worldpainter.HeightMap;
import org.pepsoft.worldpainter.heightMaps.AbstractHeightMap;
import org.pepsoft.worldpainter.heightMaps.DelegatingHeightMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 *
 * @author pepijn
 */
public class HeightMapTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof HeightMap) {
            HeightMap heightMap = (HeightMap) value;
            String name = null;
            String role = null;
            if (value instanceof AbstractHeightMap) {
                DelegatingHeightMap parent = ((AbstractHeightMap) value).getParent();
                if (parent != null) {
                    role = parent.getRole(parent.getIndex(heightMap));
                    if (parent.getHeightMapCount() > 1) {
                        name = role;
                    }
                }
            }
            if (name == null) {
                name = heightMap.getName();
            }
            if (name == null) {
                name = heightMap.getClass().getSimpleName();
            }
            if (value == focusHeightMap) {
                setBorder(focusBorder);
            } else if (getBorder() != null) {
                setBorder(null);
            }
            setText(name);
            setIcon(heightMap.getIcon());
            setToolTipText(getTooltipText(heightMap, role));
        }
        return this;
    }

    public HeightMap getFocusHeightMap() {
        return focusHeightMap;
    }

    public void setFocusHeightMap(HeightMap focusHeightMap) {
        this.focusHeightMap = focusHeightMap;
    }

    private final Border focusBorder = BorderFactory.createLineBorder(Color.RED);
    private HeightMap focusHeightMap;

    private static final Icon ICON_DISPLACEMENT_HEIGHTMAP = IconUtils.loadIcon("org/pepsoft/worldpainter/icons/arrow_rotate_anticlockwise.png");
    private static final Icon ICON_TRANSFORMING_HEIGHTMAP = IconUtils.loadIcon("org/pepsoft/worldpainter/icons/arrow_cross.png");
    private static final Icon ICON_BITMAP_HEIGHTMAP = IconUtils.loadIcon("org/pepsoft/worldpainter/icons/photo.png");
    private String getTooltipText(HeightMap heightMap, String role) {
        StringBuilder sb = new StringBuilder("<html>");
        String name = heightMap.getName();
        if (name != null) {
            sb.append("Name: <strong>").append(name).append("</strong><br>");
        }
        String type = heightMap.getClass().getSimpleName();
        type = type.substring(0, type.length() - 9);
        sb.append("Type: <strong>").append(type).append("</strong><br>");
        if (role != null) {
            sb.append("Role: <strong>").append(role).append("</strong><br>");
        }
        sb.append("</html>");
        return sb.toString();
    }
}