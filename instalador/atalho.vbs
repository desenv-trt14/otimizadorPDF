On Error Resume Next
Sub CreateShortcut(username)
On Error Resume Next
Set oWS = WScript.CreateObject("WScript.Shell")
path = oWS.RegRead("HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\{D65829C9-DA0B-43A2-BD5D-4E4F5956615F}_is1\Inno Setup: App Path")

oWS.RegWrite "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Print\Monitors\Redirected Port\Ports\RPT1:\Command", path & "\print\ps.exe", "REG_SZ"

sendTo = "c:\users\" & username & "\AppData\Roaming\Microsoft\Windows\SendTo"

sLinkFile = sendTo & "\Otimizador de PDF - JT - Juntar.lnk"

Set oLink = oWS.CreateShortcut(sLinkFile)

oLink.TargetPath = path & "\conversor.exe"
oLink.Arguments = "-j"
oLink.Save


sLinkFile = sendTo & "\Otimizador de PDF - JT - Otimizar.lnk"
Set oLink = oWS.CreateShortcut(sLinkFile)

oLink.TargetPath = path & "\conversor.exe"
oLink.Arguments = "-o -nofileoutput Mínima PJE -nosilent -nosfx"
oLink.Save


sLinkFile = sendTo & "\Otimizador de PDF - JT - Assinar.lnk"
Set oLink = oWS.CreateShortcut(sLinkFile)

oLink.TargetPath = path & "\conversor.exe"
oLink.Arguments = "-a"
oLink.Save


sLinkFile = sendTo & "\Otimizador de PDF - JT - Verificar.lnk"
Set oLink = oWS.CreateShortcut(sLinkFile)

oLink.TargetPath = path & "\conversor.exe"
oLink.Arguments = "-v"
oLink.Save


End Sub

Sub ShowFolderList(folderspec)
On Error Resume Next

    Dim fs, f, f1, fc, s
    Set fs = CreateObject("Scripting.FileSystemObject")
    Set f = fs.GetFolder(folderspec)
    Set fc = f.SubFolders
    For Each f1 in fc
        CreateShortcut(f1.name) 
    Next
End Sub

ShowFolderList("c:\users\")

