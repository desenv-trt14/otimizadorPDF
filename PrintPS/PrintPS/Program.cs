/*
 * Created by SharpDevelop.
 * User: developer
 * Date: 29/06/2017
 * Time: 08:41
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Diagnostics;
using System.IO;
using System.Runtime.InteropServices;
using System.Text.RegularExpressions;
using Microsoft.Win32;

namespace PrintPS
{
	class Program
	{


		public static void Main(string[] args)
		{
			
			bool first = true;
			string nameOfFile = "";
			
			string path = (string)Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion"
			                                        + @"\Uninstall\{D65829C9-DA0B-43A2-BD5D-4E4F5956615F}_is1","Inno Setup: App Path", @"C:\Program Files (x86)\Otimizador de PDF - JT") + "\\";
			string result = Path.GetTempPath() + Environment.TickCount + ".ps";
			BinaryWriter bw = new BinaryWriter(new FileStream(result, FileMode.Create));
			using (Stream stdin = Console.OpenStandardInput()) {
				byte[] buffer = new byte[2048];
				int bytes;
				while ((bytes = stdin.Read(buffer, 0, buffer.Length)) > 0) {
					if(first){
						String s = System.Text.Encoding.UTF8.GetString(buffer);
						first = false;
						String [] array = Regex.Split(s, "\r\n");
						foreach(String line in array){
							if(line.StartsWith("%%Title:")){
								nameOfFile = line.Replace("%%Title:", "");
							}
						}
					}
					bw.Write(buffer, 0, bytes);
				}
			}
			

			nameOfFile = String.Format("\"{0}\"", nameOfFile.Trim());
			

			result = String.Format("\"{0}\"", result);
			
			String arguments = String.Format("{0} {1}", result, nameOfFile);
			
			Process.Start(path + "conversor.exe", arguments);

			
		}
		
	}
}