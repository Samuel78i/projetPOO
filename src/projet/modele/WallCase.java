/*****************************************************************************
 * Copyright (C) [2018 - PRESENT] LiquidShare
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of LiquidShare.
 *
 * The intellectual and technical concepts contained herein are proprietary
 *  to LiquidShare and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from LiquidShare.
 *****************************************************************************/
package projet.modele;

public class WallCase extends Case{
    private String color;

    public WallCase(){
        super();
        color = "wall";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase() {
        if (isPresent()) {
            System.out.print("W  ");
        }else{
            System.out.print("-  ");
        }
    }
}
