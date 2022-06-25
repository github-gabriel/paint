package drucken;

import main.PaintMain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;

public class PrintObject implements Printable {

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex > 0){
            return NO_SUCH_PAGE;
        }

        Graphics2D g2D = (Graphics2D) graphics;
        PaintMain.getWindow().getPanel().paintAll(g2D);

        g2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        return PAGE_EXISTS;
    }

}
