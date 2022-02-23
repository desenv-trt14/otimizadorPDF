Sub RemoveShortcut(username)
On Error Resume Next
Set oWS = WScript.CreateObject("WScript.Shell")
path = oWS.RegRead("HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\{D65829C9-DA0B-43A2-BD5D-4E4F5956615F}_is1\Inno Setup: App Path")

sendTo = "c:\users\" & username & "\AppData\Roaming\Microsoft\Windows\SendTo"

Set filesys = CreateObject("Scripting.FileSystemObject") 

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - JT - Juntar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - JT - Otimizar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - JT - Assinar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - JT - Verificar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - TRT14 - Juntar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - TRT14 - Otimizar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - TRT14 - Assinar.lnk")

filesys.DeleteFile(sendTo &  "\Otimizador de PDF - TRT14 - Verificar.lnk")


End Sub

Sub ShowFolderList(folderspec)
On Error Resume Next

    Dim fs, f, f1, fc, s
    Set fs = CreateObject("Scripting.FileSystemObject")
    Set f = fs.GetFolder(folderspec)
    Set fc = f.SubFolders
    For Each f1 in fc
		RemoveShortcut(f1.name) 
    Next
End Sub

ShowFolderList("c:\users\")
