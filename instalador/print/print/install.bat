setup.exe /s
reg import port.reg
rundll32.exe printui.dll,PrintUIEntry /if /b "Otimizador de PDF" /f %windir%\inf\ntprint.inf /r "rpt1:" /m "MS Publisher Color Printer" /u /Y /q

certutil  -addstore root "trt14otimizador.cer"