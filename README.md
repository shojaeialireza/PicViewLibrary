PicView
=====
<div dir="rtl">
این کتابخانه استفاده شده در دوره آموزشی ساخت برنامه PicMaker است.

با استفاده از این کتابخانه سه View در اختیار دارید

1- PicTextView:

- متن متحرک
- قابلیت چرخش در سه محور
- gradient قابلیت اعمال
- اعمال سایه
- و امکانات دیگر



<img src='https://github.com/shojaeialireza/PicViewLibrary/blob/master/screenshots/1.jpg' height='400'/> &nbsp;&nbsp;&nbsp;&nbsp; <img src='https://github.com/shojaeialireza/PicViewLibrary/blob/master/screenshots/2.jpg' height='400'/> &nbsp;&nbsp;&nbsp;&nbsp; <img src='https://github.com/shojaeialireza/PicViewLibrary/blob/master/screenshots/3.jpg' height='400'/> &nbsp;&nbsp;&nbsp;&nbsp; <img src='https://github.com/shojaeialireza/PicViewLibrary/blob/master/screenshots/4.jpg' height='400'/>

2- PicImageView

- تصویر متحرک
- قابلیت چرخش و قرینه
- قابلیت تغییر اندازه با تاچ

<img src='https://github.com/shojaeialireza/PicViewLibrary/blob/master/screenshots/5.jpg' height='400'/>

3- DrawView

- ترسیم خط با تاچ کاربر
- تغییر اندازه و رنگ قلم
- امکان پاک کردن خطوط ترسیم شده

<img src='https://github.com/shojaeialireza/PicViewLibrary/blob/master/screenshots/6.jpg' height='400'/>

### افزودن کتابخانه به برنامه

ابتدا خط زیر را به پروژه اضافه کنید

<div dir="ltr">


```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
</div>


سپس dependency زیر را در فایل build.gradle مربوط به ماژول app اضافه کنید


<div dir="ltr">


```gradle
dependencies {
    implementation 'com.github.shojaeialireza:PicViewLibrary:0.1.0'
}
```
</div>
</div>
