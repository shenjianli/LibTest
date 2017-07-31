@echo off
set month=11��
::echo %date:~0,4%%date:~5,2%%date:~8,2%-%time:~0,2%%time:~3,2%%time:~6,2%
::20161021-123725
set date=%date:~0,4%%date:~5,2%%date:~8,2%-%time:~0,2%%time:~3,2%-%month%
echo %date%

::Դapk��ַ
set source=.\ShenMall\build\outputs\apk
set target1=D:\shen\dev\doc\����\���\apk\%date%
set target2=\\122.19.173.227\document\����android\%date%

if exist %target1% (
   rd /s /q  %target1%
   md %target1%
   echo "�Ѿ������ļ���"
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
   echo "�Ѿ������ļ���"
) else (
   md %target2%
)

for /r %source% %%a in (*_release_*) do (
    xcopy /s /e "%%a"  "%target2%\"
)
::copy %source% %target1%

