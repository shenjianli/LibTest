@echo off
set month=11月
::echo %date:~0,4%%date:~5,2%%date:~8,2%-%time:~0,2%%time:~3,2%%time:~6,2%
::20161021-123725
set date=%date:~0,4%%date:~5,2%%date:~8,2%-%time:~0,2%%time:~3,2%-%month%
echo %date%

::源apk地址
set source=.\ICBCMall\build\outputs\apk
set target1=D:\Icbc\dev\doc\资料\打包\apk\%date%
set target2=\\122.19.173.227\document\工行android\%date%

if exist %target1% (
   rd /s /q  %target1%
   md %target1%
   echo "已经存在文件夹"
) else (
   md %target1%
)
for /r %source% %%a in (*_release_*) do (
    xcopy /s /e "%%a"  "%target1%\"
)
::copy %source% %target1%

if exist %target2% (
   rd /s /q  %target2%
   md %target2%
   echo "已经存在文件夹"
) else (
   md %target2%
)

for /r %source% %%a in (*_release_*) do (
    xcopy /s /e "%%a"  "%target2%\"
)
::copy %source% %target1%

