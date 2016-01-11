/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package javafxapplication1;

import java.util.ArrayList;
import java.util.Random;
import javafx.collections.ObservableIntegerArray;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Seazic1
 */
public class RandMap3D extends TriangleMesh{
    private double maxhight;
    public RandMap3D(float sx,float sy,float ex,float ey) {
//        vartexsize = 0;
    this.getPoints().addAll(sx,sy,0,ex,sy,0,sx,ey,0,ex,ey,0);
        float[] texC = new float[200];
                for(int cnt = 0;cnt < 100;cnt++){
                    texC[0+(cnt*2)] = 1.0f-((float)cnt)/100.0f;
                    texC[1+(cnt*2)] = 0.0f;
                }
        this.getTexCoords().addAll(texC);
        this.getFaces().addAll(2,50,1,50,0,50,1,50,2,50,3,50);
        maxhight = Math.sqrt(((sx*sx)+(sy*sy)+(ex*ex)+(ey*ey))/4)/10;
        addNest(4);
    }
    public void addNest(int nest){
        if(nest > 0)
            addNest(--nest);
        int[] facecopy= this.getFaces().toArray(new int[0]);
        System.out.println(" "+
                facecopy.length+" "+this.getFaces().size()+
                " point " + this.getPointElementSize()+
                " texco " + this.getTexCoordElementSize()+
                " facee " + this.getFaceElementSize());
        this.getFaces().clear();
        int triElem = 3;
        int dimElem = this.getPointElementSize();
        int texElem = this.getTexCoordElementSize();
        int faceElem = this.getFaceElementSize();
        int Elem = this.getNormalElementSize();
        for(int cnt = 0;cnt < (int)(facecopy.length/faceElem);cnt++){
            float[] facepoints = new float[triElem*dimElem];
            float[] newpoint;
            for(int cnt2 = 0;cnt2 < triElem;cnt2++){
                int pointIndex = facecopy[(cnt*faceElem)+(cnt2*texElem)];
                this.getPoints().copyTo(pointIndex*dimElem, facepoints, cnt2*dimElem, dimElem);
            }
            System.out.println("javafxapplication1.RandMap3D.addNest() "+this.getNormals().size());
            newpoint = createPoint(facepoints, nomarize(facepoints));
            int texcolor = 50+(int)(newpoint[2]*50/maxhight);
            if(texcolor < 0)
                texcolor = 0;
            if(texcolor > 99)
                texcolor = 99;
            for(float f:newpoint){
                System.out.print(" "+f);
            }
            int newIndex = this.getPoints().size()/dimElem;
            this.getPoints().addAll(newpoint);
            int [] trifaceIndex = new int[triElem];
            int [] trifacetex = new int[triElem];
            for(int cnt2 = 0;cnt2 < triElem;cnt2++){
                trifaceIndex[cnt2] = facecopy[(cnt*faceElem)+(cnt2*texElem)];
                trifacetex[cnt2] = facecopy[(cnt*faceElem)+(cnt2*texElem)+(texElem-1)];
                
            }
            for(int cnt2 = 0;cnt2 < triElem;cnt2++){
                System.out.println(" "+newIndex+" "+texcolor);
                this.getFaces().addAll(trifaceIndex[cnt2],trifacetex[cnt2],
                                        trifaceIndex[(cnt2+1)%3],trifacetex[(cnt2+1)%3],
                                        newIndex,texcolor);
            }
//            int newVer = createPoint(facecopy);
            System.out.println("");
        }
//       this.getFaces().clear();

    }
    public float [] createPoint(float [] vertexs,float [] nomarize){
        float[] point = new float[3];
        float randmax = 30.0f;
        float rand = randmax-(2*randmax*(new Random()).nextFloat());
        for(int cnt = 0;cnt < point.length;cnt++){
            point[cnt] = 0.0f;
            int pointnum = vertexs.length/point.length;
            for(int cnt2 = 0;cnt2 < pointnum;cnt2++){
                 point[cnt] += vertexs[cnt2*point.length+cnt];
            }
            point[cnt] /= pointnum;
            if(point.length == nomarize.length)
            point[cnt] += nomarize[cnt]*rand;
        }
        return point;
    }
    public float [] nomarize(float [] vertex){
        float[] v1 = new float[3];
        float[] v2 = new float[3];
        float[] v3 = new float[3];
        float length = 0.0f;
        
        for(int cnt = 0;cnt < 3;cnt++){
            v1[cnt] = vertex[cnt]-vertex[cnt+3];
        }
        for(int cnt = 0;cnt < 3;cnt++){
            v2[cnt] = vertex[cnt+6]-vertex[cnt+3];
        }
        for(int cnt = 0;cnt < 3;cnt++){
            v3[cnt] = (v1[(1+cnt)%3]*v2[(2+cnt)%3])-(v1[(2+cnt)%3]*v2[(1+cnt)%3]);
            length += Math.pow(v3[cnt],2.0);
        }
        length = (float)Math.sqrt(length);
        for(int cnt = 0;cnt < 3;cnt++){
            v3[cnt] /= length;
            
        }
        return v3;
    }
}
