package com.ifsc.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
<<<<<<< HEAD
=======
import android.graphics.RectF;
>>>>>>> 9a31030 (EAD paint)
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SimplePaint extends View {
<<<<<<< HEAD
    Path mpath;
    Paint mpaint;

    ArrayList<Paint> paintList;
    ArrayList<Path> pathList;
    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpath = new Path();
        mpaint = new Paint();
        mpaint.setStrokeWidth(5);
        mpaint.setColor(0xFF000000);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setAntiAlias(true);
        paintList = new ArrayList<>();
        pathList = new ArrayList<>();

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paintList.size(); i++) {
            canvas.drawPath(pathList.get(i), paintList.get(i));
        }
        canvas.drawPath(mpath, mpaint);
    }

=======

    // Tipos de forma disponíveis
    public enum ShapeType {
        FREE_DRAW,
        RECTANGLE,
        CIRCLE
    }

    // Representa uma forma salva (histórico)
    private static class DrawnShape {
        Paint paint;
        Path path;         // usado para linha livre
        ShapeType type;
        float startX, startY, endX, endY; // usado para retângulo e círculo

        DrawnShape(Paint paint, Path path, ShapeType type, float sx, float sy, float ex, float ey) {
            this.paint = paint;
            this.path = path;
            this.type = type;
            this.startX = sx;
            this.startY = sy;
            this.endX = ex;
            this.endY = ey;
        }
    }

    private Path mPath;
    private Paint mPaint;

    private float startX, startY; // ponto inicial do toque

    private ShapeType currentShape = ShapeType.FREE_DRAW;

    private final ArrayList<DrawnShape> shapeList = new ArrayList<>();

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStrokeWidth(8);
        mPaint.setColor(0xFF000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    // ─── Desenho ──────────────────────────────────────────────────────────────

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Desenha o histórico de formas
        for (DrawnShape shape : shapeList) {
            drawShape(canvas, shape.paint, shape.type, shape.path,
                    shape.startX, shape.startY, shape.endX, shape.endY);
        }

        // Desenha a forma atual (em construção)
        drawShape(canvas, mPaint, currentShape, mPath, startX, startY, -1, -1);
    }

    private void drawShape(Canvas canvas, Paint paint, ShapeType type,
                           Path path, float sx, float sy, float ex, float ey) {
        switch (type) {
            case FREE_DRAW:
                canvas.drawPath(path, paint);
                break;
            case RECTANGLE:
                if (ex != -1) {
                    canvas.drawRect(Math.min(sx, ex), Math.min(sy, ey),
                            Math.max(sx, ex), Math.max(sy, ey), paint);
                }
                break;
            case CIRCLE:
                if (ex != -1) {
                    float radius = (float) Math.hypot(ex - sx, ey - sy);
                    canvas.drawCircle(sx, sy, radius, paint);
                }
                break;
        }
    }

    // ─── Toque ────────────────────────────────────────────────────────────────
>>>>>>> 9a31030 (EAD paint)

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
<<<<<<< HEAD
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mpath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                mpath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                mpath.lineTo(x, y);
                break;
            default:
                return false;
        }
        //path.moveTo(x,y);
        //path.lineTo(x, y);
        invalidate();
        return true;
        //return super.onTouchEvent(event);
    }
    public void setColor(int color){
        //criar nova camada de paint e path
        paintList.add(mpaint);
        pathList.add(mpath);
        mpath = new Path();
        mpaint = new Paint(mpaint);

        mpaint.setColor(color);
        invalidate();

    }
}

=======

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                if (currentShape == ShapeType.FREE_DRAW) {
                    mPath.moveTo(x, y);
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if (currentShape == ShapeType.FREE_DRAW) {
                    mPath.lineTo(x, y);
                } else {
                    // Para retângulo/círculo atualiza o ponto final e redesenha
                    // "endX/endY" são guardados temporariamente via invalidate
                    endPreviewX = x;
                    endPreviewY = y;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                if (currentShape == ShapeType.FREE_DRAW) {
                    mPath.lineTo(x, y);
                    // Salva a forma
                    shapeList.add(new DrawnShape(new Paint(mPaint), mPath,
                            ShapeType.FREE_DRAW, 0, 0, 0, 0));
                    mPath = new Path();
                } else {
                    // Salva retângulo ou círculo
                    shapeList.add(new DrawnShape(new Paint(mPaint), null,
                            currentShape, startX, startY, x, y));
                    endPreviewX = -1;
                    endPreviewY = -1;
                }
                invalidate();
                break;

            default:
                return false;
        }
        return true;
    }

    // Coordenadas de prévia durante o arraste (retângulo/círculo)
    private float endPreviewX = -1, endPreviewY = -1;

    @Override
    protected void onDrawForeground(@NonNull Canvas canvas) {
        super.onDrawForeground(canvas);
        // Prévia da forma em construção (retângulo/círculo)
        if (currentShape != ShapeType.FREE_DRAW && endPreviewX != -1) {
            drawShape(canvas, mPaint, currentShape, null,
                    startX, startY, endPreviewX, endPreviewY);
        }
    }

    // ─── API pública ──────────────────────────────────────────────────────────

    /** Muda a cor do traço atual */
    public void setStrokeColor(int color) {
        mPaint = new Paint(mPaint);
        mPaint.setColor(color);
        invalidate();
    }

    /** Define o tipo de forma a desenhar */
    public void setShapeType(ShapeType shape) {
        currentShape = shape;
    }

    /** Limpa todo o canvas */
    public void clearCanvas() {
        shapeList.clear();
        mPath = new Path();
        endPreviewX = -1;
        endPreviewY = -1;
        invalidate();
    }
}
>>>>>>> 9a31030 (EAD paint)
