# IntelligentIdentification
智能识别Demo

## 说明
1、Demo只作身份证与车牌识别演示。其它请自行选择使用  
2、demo未做权限适配 需自行开启

识别器可选择，这里有你常用的二维码/条码识别，还有你可能用到的身份证识别、银行卡识别、车牌识别、图片文字识别、黄图识别、驾驶证识别，
如果没有你想要的，可以自定义识别器。该库只识别扫描框内的图像，识别速率上大大提高，
而且这个库比起其它的库就是解决了摄像头预览变形，预览页面高度自定义，
你可以像常规一样整个页面都是预览，或者你可以选择在任何位置定义任何尺寸的预览，扫描框也高度自定义，
你可以像常规一样居中，或者你也可以在预览的任何位置定义任何尺寸的扫描框（实际识别的扫描框和画上去的扫描框不一定是一样的，由你自己决定）。


## 使用
### 依赖
```
implementation 'com.shouzhong:Scanner:1.1.3'
```
以下选择自己需要的
```
// zxing
implementation 'com.google.zxing:core:3.3.3'
// zbar
implementation 'com.shouzhong:ScannerZBarLib:1.0.0'
// 银行卡识别
implementation 'com.shouzhong:ScannerBankCardLib:1.0.3'
// 身份证识别
implementation 'com.shouzhong:ScannerIdCardLib:1.0.4'
// 车牌识别
implementation 'com.shouzhong:ScannerLicensePlateLib:1.0.3'
// 图片文字识别
implementation 'com.shouzhong:ScannerTextLib:1.0.0'
// 黄图识别
implementation 'com.shouzhong:ScannerNsfwLib:1.0.0'
// 驾驶证识别
implementation 'com.shouzhong:ScannerDrivingLicenseLib:1.0.1'
// 身份证识别（第二种方式）
implementation 'com.shouzhong:ScannerIdCard2Lib:1.0.0'
```
```
开启或者关闭某个识别器
```
// 启用zxing识别器
scannerView.setEnableZXing(true)

// 启用zbar识别器
scannerView.setEnableZBar(true)

// 启用银行卡识别器
scannerView.setEnableBankCard(true)

// 启用身份证识别器（这里只支持2代身份证）
scannerView.setEnableIdCard(true)

// 启用车牌识别
scannerView.setEnableLicensePlate(true)

// 启用驾驶证识别
scannerView.setEnableDrivingLicense(true)

// 启用身份证识别（第二种方式）
scannerView.setEnableIdCard2(true)

```
如果你想自定义识别器
```
scannerView.setScanner(new IScanner() {

    /**
     * 这里实现自己的识别器，并把识别结果返回
     *
     * @param data 矩形框内nv21图像数据
     * @param width 图像宽度
     * @param height 图像高度
     * @return
     * @throws Exception
     */
     
    @Override
    public Result scan(byte[] data, int width, int height) throws Exception {
        // 如果你想转为Bitmap，请使用NV21.nv21ToBitmap(byte[] nv21, int width, int height)
        return null;
    }
    
});
```

## 回调说明

Result

属性 | 说明
------------ | -------------
TYPE_CODE | 类型：二维码/条码
TYPE_ID_CARD_FRONT | 类型：身份证人头面
TYPE_ID_CARD_BACK | 类型：身份证国徽面
TYPE_BANK_CARD | 类型：银行卡
TYPE_LICENSE_PLATE | 类型：车牌
TYPE_DRIVING_LICENSE | 类型：驾驶证
type | 结果类型
path | 保存的图片路径
data | 数据
```
// 以下是对data的说明
// 当type为TYPE_CODE，TYPE_BANK_CARD，TYPE_LICENSE_PLATE时，data为字符串
// 当type为TYPE_ID_CARD_FRONT时，data为json字符串，格式如下
{
	"cardNumber": "21412412421",// 身份证号
	"name": "张三",// 姓名
	"sex": "男",// 性别
	"nation": "汉",// 民族
	"birth": "1999-01-01",// 出生
	"address": "地址"// 地址
}
// 当type为TYPE_ID_CARD_BACK时，data为json字符串，格式如下
{
	"organization": "签发机关",// 签发机关
	"validPeriod": "20180101-20380101"// 有效期限
}
// 当type为TYPE_DRIVING_LICENSE时，data为json字符串，格式如下
{
	"cardNumber": "43623446432",// 证号
	"name": "张三",// 姓名
	"sex": "男",// 性别
	"nationality": "中国",// 国籍
	"address": "地址",// 地址
	"birth": "1999-01-01",// 出生日期
	"firstIssue": "2018-01-01",// 初次领证日期
	"_class": "C1",// 准驾车型
	"validPeriod": "20180101-20240101"// 有效期限
}
```

BankCardInfoBean

属性或方法 | 说明
------------ | -------------
cardNumber | 银行卡号
cardType | 银行卡（英文）类型
bank | 银行（英文）名称
getCNBankName | 获取银行（中文）名称
getBankId | 获取银行编号
getCNCardType | 获取银行卡（中文）类型

## 方法说明

ScannerView

方法名 | 说明
------------ | -------------
setViewFinder | 扫描区域
setCallback | 扫码成功后的回调
setCameraDirection | 摄像头方向，后置为Camera.CameraInfo.CAMERA_FACING_BACK，前置为Camera.CameraInfo.CAMERA_FACING_FRONT
setEnableZXing | 是否启用zxing识别器，默认false
setEnableZBar | 是否启用zbar识别器，默认false
setEnableQrcode | 是否启动二维码识别，默认true，只有在zxing或者zbar开启时有效
setEnableBarcode | 是否启动条码识别，默认true，只有在zxing或者zbar开启时有效
setEnableBankCard | 是否启用银行卡识别器，默认false
setEnableIdCard | 是否启用身份证识别器，默认false
setEnableIdCard2 | 是否启用身份证识别器（第二种方式），默认false
setEnableDrivingLicense | 是否启用驾驶证识别器，默认false
setEnableLicensePlate | 是否启用车牌识别器，默认false
setScanner | 自定义识别器
onResume | 开启扫描
onPause | 停止扫描
restartPreviewAfterDelay | 设置多少毫秒后重启扫描
setFlash | 开启/关闭闪光灯
toggleFlash | 切换闪光灯的点亮状态
isFlashOn | 闪光灯是否被点亮
setShouldAdjustFocusArea | 设置是否要根据扫码框的位置去调整对焦区域的位置，部分手机不支持，默认false
setSaveBmp | 设置是否保存识别的图片，默认false
setRotateDegree90Recognition | 是否在原来识别的图像基础上旋转90度继续识别，默认false

ScannerUtils

方法名 | 说明
------------ | -------------
decodeCode | 二维码/条码识别，建议在子线程运行
decodeBank | 银行卡识别，建议在子线程运行
getBankCardInfo | 获取银行卡信息，请在子线程运行
decodeIdCard | 身份证识别，建议在子线程运行
decodeId2Card | 身份证识别（第二种方式），建议在子线程运行
decodeDrivingLicense | 驾驶证识别，建议在子线程运行
decodeLicensePlate | 车牌识别，建议在子线程运行
decodeText | 图片文字识别，请在子线程运行
decodeNsfw | 黄图识别，大于0.3可以说图片涉黄，建议在子线程运行
createBarcode | 条码生成，建议在子线程运行
createQRCode | 二维码生成，建议在子线程运行
addLogo | 往图片中间加logo

NV21

方法名 | 说明
------------ | -------------
nv21ToBitmap | nv21转bitmap
bitmapToNv21 | bitmap转nv21

## 注意事项
1. so资源只有arm格式的，ScannerDrivingLicenseLib和ScannerIdCard2Lib无arm64-v8a格式

## 如需商用请联系：
https://github.com/shouzhong/Scanner
