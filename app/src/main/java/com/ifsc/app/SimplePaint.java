
package com.ifsc.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SimplePaint extends View {

    // Tipos de desenho
    public enum ShapeType {
        FREE_DRAW,
        RECTANGLE,
        CIRCLE
    }

    // Classe para armazenar os desenhos
    private static class DrawnShape {
        Paint paint;
        Path path;
        ShapeType type;
        float startX, startY, endX, endY;

        DrawnShape(Paint paint, Path path, ShapeType type,
                   float sx, float sy, float ex, float ey) {
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

    private ShapeType currentShape = ShapeType.FREE_DRAW;

    private final ArrayList<DrawnShape> shapeList = new ArrayList<>();

    private float startX, startY;
    private float endPreviewX = -1;
    private float endPreviewY = -1;

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    // Configuração inicial do pincel
    private void initPaint() {
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setColor(0xFF000000);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    // Desenha na tela
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Desenha formas já salvas
        for (DrawnShape shape : shapeList) {
            drawShape(canvas, shape.paint, shape.type,
                    shape.path, shape.startX, shape.startY,
                    shape.endX, shape.endY);
        }

        // Desenha linha livre em construção
        if (currentShape == ShapeType.FREE_DRAW) {
            canvas.drawPath(mPath, mPaint);
        }

        // Prévia do retângulo ou círculo
        if (currentShape != ShapeType.FREE_DRAW
                && endPreviewX != -1) {

            drawShape(canvas, mPaint, currentShape,
                    null, startX, startY,
                    endPreviewX, endPreviewY);
        }
    }

    // Desenha cada tipo de forma
    private void drawShape(Canvas canvas, Paint paint,
                           ShapeType type, Path path,
                           float sx, float sy,
                           float ex, float ey) {

        switch (type) {

            case FREE_DRAW:
                if (path != null) {
                    canvas.drawPath(path, paint);
                }
                break;

            case RECTANGLE:
                canvas.drawRect(
                        Math.min(sx, ex),
                        Math.min(sy, ey),
                        Math.max(sx, ex),
                        Math.max(sy, ey),
                        paint
                );
                break;

            case CIRCLE:
                float radius = (float) Math.hypot(
                        ex - sx, ey - sy
                );

                canvas.drawCircle(sx, sy, radius, paint);
                break;
        }
    }

    // Detecta os movimentos do dedo
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                startX = x;
                startY = y;

                endPreviewX = x;
                endPreviewY = y;

                if (currentShape == ShapeType.FREE_DRAW) {
                    mPath = new Path();
                    mPath.moveTo(x, y);
                }

                invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:

                if (currentShape == ShapeType.FREE_DRAW) {
                    mPath.lineTo(x, y);
                } else {
                    endPreviewX = x;
                    endPreviewY = y;
                }

                invalidate();
                return true;

            case MotionEvent.ACTION_UP:

                if (currentShape == ShapeType.FREE_DRAW) {

                    mPath.lineTo(x, y);

                    shapeList.add(new DrawnShape(
                            new Paint(mPaint),
                            mPath,
                            ShapeType.FREE_DRAW,
                            0, 0, 0, 0
                    ));

                    mPath = new Path();

                } else {

                    shapeList.add(new DrawnShape(
                            new Paint(mPaint),
                            null,
                            currentShape,
                            startX,
                            startY,
                            x,
                            y
                    ));

                    endPreviewX = -1;
                    endPreviewY = -1;
                }

                invalidate();
                return true;

            case MotionEvent.ACTION_CANCEL:

                endPreviewX = -1;
                endPreviewY = -1;
                mPath = new Path();

                invalidate();
                return true;
        }

        return false;
    }

    // Altera a cor do pincel
    public void setStrokeColor(int color) {
        mPaint = new Paint(mPaint);
        mPaint.setColor(color);
        invalidate();
    }

    // Altera a ferramenta de desenho
    public void setShapeType(ShapeType shape) {
        currentShape = shape;
        mPath = new Path();
        endPreviewX = -1;
        endPreviewY = -1;
        invalidate();
    }

    // Limpa todos os desenhos
    public void clearCanvas() {
        shapeList.clear();
        mPath = new Path();

        endPreviewX = -1;
        endPreviewY = -1;

        invalidate();
    }
}
