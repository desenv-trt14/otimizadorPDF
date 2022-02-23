===============================================================
README.TXT         2012-06-21
===============================================================

RedMon - Redirection Port Monitor
for Windows 7, Vista and XP SP3.
Version 1.9

========
Overview
========
 The RedMon port monitor redirects a special printer port
 to a program.  RedMon is commonly used with Ghostscript and 
 a non-PostScript printer to emulate a PostScript printer,
 or with Ghostscript as a PDF writer.

 RedMon can be used with any program that accepts data on
 standard input.

 Using RedMon you create redirected printer ports.  If you connect a
 Windows printer driver to the redirected printer port, all data sent
 to the redirected port will be forwarded by RedMon to the
 standard input of a program.  This program is then responsible
 for processing the data and producing new output.

 RedMon should only be used for local printing.  
 Using it shared across a network has problem with the print
 job not being owned by the user who submitted the job.

 RedMon can create a PDF writer using a PostScript printer driver 
 and the Ghostscript pdfwrite output device.

 Configuring `RedMon` is not a job for novices.

 Supports Windows 7, Vista and XP SP3.
 The older RedMon 1.7 supports Windows 95/98, Windows XP SP1, 
 Windows 2000 and NT 4.0.

============
Installation
============
 To read the documentation, open 'redmon.chm'
 To install, run 'setup.exe' for 32-bit, or 'setup32.exe" for 64-bit.

=================
RedPr - RED PRint
=================
 A command line utility "redpr" is included to copy files to
 a printer.  This is similar to the GSview "File | Print File"
 or the DOS "COPY /B filename LPT1:".
 RedPr copies files to a specified Windows printer queue.
 The RedMon install program does not install RedPr.
 If you want to use RedPr, you will need to copy it to
 a directory on your path.


 Syntax is 
   redpr [-P"printer"] [-pport] [-b] [-h] [-v] filename ...
     -P"printer"  Specify target printer
     -pport       Target port
     -b           Bi-directional.  Attempt to read from printer.
     -h           Help
     -v           Verbose.  Display debugging messages.

 Printer names may be either the Windows 95/NT printer name
 such as "Apple LaserWriter II NT", or a port name such
 as "LPT1:".   If a port name is used, redpr will replace it 
 with the name of the first printer to use that port.
   
 Examples:
     redpr -P"Apple LaserWriter II NT" file.ps
     redpr -pLPT1: file.ps
     redpr file.ps

 Actually, -P and -p are interchangeable.  RedPr first looks
 for a printer with the given name. If that fails it tries to 
 find a printer which uses the given port.  The default printer 
 will be used if no printer or port is specified.

 Bi-directional mode works under Windows 95 with a communications port.
 It will not work at all under Windows NT.
 When using bi-directional mode, the file is sent directly to
 the printer without being spooled.
 Bi-directional operation is slow because for each 4096 bytes written 
 to the printer, an attempt is made to read from the printer,
 which usually blocks for a few seconds until a timeout occurs.

 If you get sick of typing in long printer names, make a batch
 file wpr.bat which contains
   @redpr -P"Apple LaserWriter II NT" %1 %2 %3 %4
 with your appropriate printer name.

=========================
RedRun - Redirect and Run
=========================
 RedRun copies standard input to a temporary file, then runs a 
 program.  RedRun is designed to link RedMon with a program that 
 wants its input in a file, not stdin.
 
 An example for Windows 95 is running GSview.
 In RedMon, the program to run would be
   c:\gstools\redrun.exe
 and the arguments would be
   c:\gstools\gsview\gsview32.exe %1

 For Windows NT, it is recommended that you DO NOT USE RedRun 
 to start GSview.  RedRun and any program started by it will 
 have the same security attributes as the print spooler.
 Apart from the fact that GSview won't work because it can't
 find its initialization file, using RedRun under Windows NT
 is a security risk.

 When started, RedRun will write standard input to a 
 temporary file, until it reaches end of file.  It will then 
 start the specified program.  RedRun will wait until the 
 specified program terminates, then will delete the temporary
 and terminate itself.

==========================
RedFile - Redirect to File
==========================
 Copy standard input to a named file.
 RedFile can be used to capture printer output redirected by RedMon.

 Usage: redfile filename

 Example:
   echo test | redfile testfile.txt
   echo test > testfile.txt\n\
 These are equivalent, but when using RedMon you can't use the
 latter because '>' redirection isn't available.

 You can place an integer format specifier in filename to
 create numbered files.  For example
   redfile test%04d.txt
 First use will write test0000.txt, second use test0001.txt.

 You could could connect a printer to FILE:, but you keep getting
 a prompt for the filename.
 Instead of using RedMon and RedFile, in Windows 95 you can edit
 win.ini [Ports] to add a named file.  This might not work under
 Windows NT.

 For Windows NT, if RedFile is started from RedMon, it will run 
 with the security attributes of the print spooler.
 Any files created by RedFile might not be readable by the
 user who started the print job.


=======================
Copyright and Licensing
=======================

  Copyright (C) 1997-2012, Ghostgum Software Pty Ltd.  All rights reserved.
  
  This file is part of RedMon.
  
  This software is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  

  This software is distributed under licence and may not be copied, modified
  or distributed except as expressly authorised under the terms of the
  LICENCE.


     Author: Russell Lang, Ghostgum Software Pty Ltd
   Internet: http://www.ghostgum.com.au/

==============================================================

