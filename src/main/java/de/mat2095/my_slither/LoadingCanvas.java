package de.mat2095.my_slither;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class LoadingCanvas extends JPanel implements Runnable {

    private class Circle
    {
        private double x,y;
        private double r;
        private Color col;

        /**
         *
         * @param x    //x coord of circle
         * @param y    //y coord of circle
         * @param r    //radius of circle
         * @param col  //color of circle
         */
        public Circle(double x, double y, double r, Color col)
        {
            this.x = x;
            this.y = y;
            this.r = r;
            this.col = col;
        }

    }

    private final static int FPS = 60;
    private final static int ANIMATION_FRAMES = 150;

    //circles in the animation. Animation is around (0,0) then is translated to centre screen before drawing
    private Circle[] circles = new Circle[6];

    private boolean running;
    private final static double frameTime = 1000.0/FPS; // length of a frame in milliseconds
    private Dimension centre;

    private double spinningRadius = 5;


    public LoadingCanvas()
    {
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle(0, 0, 10,Color.CYAN);
        }
    }


    public void run()
    {


        running = true;
        int frameCount = 0;
        while(running)
        {
            long startTime = System.currentTimeMillis();
            update(frameCount);
            repaint();
            long delay = System.currentTimeMillis() - startTime;

            try{
                Thread.sleep((long)Math.max(0,(frameTime-delay)));
            }catch(InterruptedException e){
                //continue executing
            }

            frameCount++;
        }

    }

    public void endAnim()
    {
        running = false;
    }


    private void update(int frameCount)
    {
        frameCount %= ANIMATION_FRAMES;
        if(frameCount < 30)
            moveToCircle(30-frameCount);
        else if(frameCount < 90)
            spinAroundCentre();
        else if(frameCount<120)
            moveToCentre(120 - frameCount);
        //pause remainder of animation frames
    }

    private void spinAroundCentre()
    {

        double moveAngle = Math.PI/20;

        double cosT = Math.cos(moveAngle);
        double sinT = Math.sin(moveAngle);

        for (Circle c :circles) {
            //rotate
            double oldX = c.x;
            c.x = c.x*cosT - c.y*sinT;
            c.y = c.y*cosT + oldX*sinT;
        }
    }

    /**
     * moves all circles from their location to the centre
     * @param frameCount  //the amount of frames till the animation is complete. This should be called once per frame
     */
    private void moveToCentre(int frameCount)
    {
        for (Circle c: circles) {
            double xMov = (c.x)/frameCount;
            double yMov = (c.y)/frameCount;

            c.x -=xMov;
            c.y -=yMov;
        }
    }

    /**
     * moves circles from the centre into a circle
     * @param frameCount //the amount of frames till the animation is complete. This should be called once per frame
     */
    private void moveToCircle(int frameCount)
    {
        double angle = (2*Math.PI)/ (circles.length +1); //dont make complete circle

        for (int i = 0; i < circles.length; i++) {
            double xMov = (spinningRadius * Math.cos(angle*i))/frameCount;
            double yMov = (spinningRadius * Math.sin(angle*i))/frameCount;

            circles[i].x += xMov;
            circles[i].y += yMov;
        }
    }



    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D g = (Graphics2D) graphics;

        //calc centre every frame in case of resize
        centre = getSize();
        centre.height/=2;
        centre.width/=2;

        for (Circle c: circles) {
            g.setColor(c.col);
            g.fill(new Ellipse2D.Double(c.x + centre.width,c.y + centre.height, c.r, c.r));
        }

    }



}
