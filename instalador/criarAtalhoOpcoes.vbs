Set oWS = WScript.CreateObject("WScript.Shell")
On Error Resume Next

Function CreateShortcut(username)
	On Error Resume Next
	path = oWS.RegRead("HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall\{D65829C9-DA0B-43A2-BD5D-4E4F5956615F}_is1\Inno Setup: App Path")

	sendTo = "c:\users\" & username & "\AppData\Roaming\Microsoft\Windows\SendTo"

	finalidade = Array("PJE", "Proad", "Outros")

	nivel = Array("Minima", "Boa", "Razoável", "Extrema")

	For i = 0 to UBound(finalidade)
		For j = 0 to UBound(nivel)
			sLinkFile = sendTo & "\Otimizador de PDF - JT - Otimizar - " & finalidade(i) & " - " & nivel(j) & ".lnk"
			Set oLink = oWS.CreateShortcut(sLinkFile)

			oLink.TargetPath = path & "\conversor.exe"
			oLink.Arguments = "-o -nofileoutput " & nivel(j) & " " & finalidade(i) & " -nosilent -nosfx"
			oLink.Save
		Next
	Next

	CreateShortcut = sendTo

End Function

strUser = CreateObject("WScript.Network").UserName
sendTo = CreateShortcut(strUser)
oWS.Run("explorer """ & sendTo & """")