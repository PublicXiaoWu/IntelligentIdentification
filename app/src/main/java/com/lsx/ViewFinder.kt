package com.lsx

import android.content.Context
import android.graphics.*
import android.view.View
import com.shouzhong.scanner.IViewFinder


/**
 * @Description:    扫描框（身份证）
 * @CreateDate:     2021/1/4
 * @Author:         LSX
 */
 class ViewFinder(context: Context?) : View(context), IViewFinder {
    private var framingRect //扫码框所占区域
            : Rect? = null
    private val widthRatio = 0.9f //扫码框宽度占view总宽度的比例
    private val heightRatio = 0.8f
    private val heightWidthRatio = 0.5626f //扫码框的高宽比
    private val leftOffset = -1 //扫码框相对于左边的偏移量，若为负值，则扫码框会水平居中
    private val topOffset = -1 //扫码框相对于顶部的偏移量，若为负值，则扫码框会竖直居中
    private val laserColor = -0xff7a89 // 扫描线颜色
    private val maskColor = 0x60000000 // 阴影颜色
    private val borderColor = -0xff7a89 // 边框颜色
    private val borderStrokeWidth = 12f // 边框宽度
    private val borderLineLength = 72 // 边框长度
    private val laserPaint // 扫描线
            : Paint
    private val maskPaint // 阴影遮盖画笔
            : Paint
    private val borderPaint // 边框画笔
            : Paint
    private var position = 0
    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        updateFramingRect()
    }

    override fun onDraw(canvas: Canvas) {
        if (getFramingRect() == null) {
            return
        }
        drawViewFinderMask(canvas)
        drawViewFinderBorder(canvas)
        drawLaser(canvas)
    }

    private fun drawLaser(canvas: Canvas) {
        val framingRect: Rect = getFramingRect()
        val top: Int = framingRect.top + 10 + position
        canvas.drawRect((framingRect.left + 10).toFloat(), top.toFloat(), (framingRect.right - 10).toFloat(), (top + 5).toFloat(), laserPaint)
        position = if (framingRect.bottom - framingRect.top - 25 < position) 0 else position + 2
        //区域刷新
        postInvalidateDelayed(20, framingRect.left + 10, framingRect.top + 10, framingRect.right - 10, framingRect.bottom - 10)
    }

    /**
     * 绘制扫码框四周的阴影遮罩
     */
    private fun drawViewFinderMask(canvas: Canvas) {
        val width: Int = canvas.width
        val height: Int = canvas.height
        val framingRect: Rect = getFramingRect()
        canvas.drawRect(0F, 0F, width.toFloat(), framingRect.top.toFloat(), maskPaint) //扫码框顶部阴影
        canvas.drawRect(0F, framingRect.top.toFloat(), framingRect.left.toFloat(), framingRect.bottom.toFloat(), maskPaint) //扫码框左边阴影
        canvas.drawRect(framingRect.right.toFloat(), framingRect.top.toFloat(), width.toFloat(), framingRect.bottom.toFloat(), maskPaint) //扫码框右边阴影
        canvas.drawRect(0F, framingRect.bottom.toFloat(), width.toFloat(), height.toFloat(), maskPaint) //扫码框底部阴影
    }

    /**
     * 绘制扫码框的边框
     */
    private fun drawViewFinderBorder(canvas: Canvas) {
        val framingRect: Rect = getFramingRect()

        // Top-left corner
        val path = Path()
        path.moveTo(framingRect.left.toFloat(), (framingRect.top + borderLineLength).toFloat())
        path.lineTo(framingRect.left.toFloat(), framingRect.top.toFloat())
        path.lineTo((framingRect.left + borderLineLength).toFloat(), framingRect.top.toFloat())
        canvas.drawPath(path, borderPaint)

        // Top-right corner
        path.moveTo(framingRect.right.toFloat(), (framingRect.top + borderLineLength).toFloat())
        path.lineTo(framingRect.right.toFloat(), framingRect.top.toFloat())
        path.lineTo((framingRect.right - borderLineLength).toFloat(), framingRect.top.toFloat())
        canvas.drawPath(path, borderPaint)

        // Bottom-right corner
        path.moveTo(framingRect.right.toFloat(), (framingRect.bottom - borderLineLength).toFloat())
        path.lineTo(framingRect.right.toFloat(), framingRect.bottom.toFloat())
        path.lineTo((framingRect.right - borderLineLength).toFloat(), framingRect.bottom.toFloat())
        canvas.drawPath(path, borderPaint)

        // Bottom-left corner
        path.moveTo(framingRect.left.toFloat(), (framingRect.bottom - borderLineLength).toFloat())
        path.lineTo(framingRect.left.toFloat(), framingRect.bottom.toFloat())
        path.lineTo((framingRect.left + borderLineLength).toFloat(), framingRect.bottom.toFloat())
        canvas.drawPath(path, borderPaint)
    }

    /**
     * 设置framingRect的值（扫码框所占的区域）
     */
    @Synchronized
    private fun updateFramingRect() {
        val viewSize = Point(width, height)
        var width = width * 801 / 1080
        var height = getWidth() * 811 / 1080
        width = (getWidth() * widthRatio).toInt()
        //            height = (int) (getHeight() * heightRatio);
        height = (heightWidthRatio * width).toInt()
        val left: Int
        val top: Int
        left = if (leftOffset < 0) {
            (viewSize.x - width) / 2 //水平居中
        } else {
            leftOffset
        }
        top = if (topOffset < 0) {
            (viewSize.y - height) / 2 //竖直居中
        } else {
            topOffset
        }
        framingRect = Rect(left, top, left + width, top + height)
    }

    override fun getFramingRect(): Rect {
        return framingRect!!
    }

    init {
        setWillNotDraw(false) //需要进行绘制
        laserPaint = Paint()
        laserPaint.color = laserColor
        laserPaint.style = Paint.Style.FILL
        maskPaint = Paint()
        maskPaint.color = maskColor
        borderPaint = Paint()
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderStrokeWidth
        borderPaint.isAntiAlias = true
    }
}
