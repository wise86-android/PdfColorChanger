[![Open Source Saturday](https://img.shields.io/badge/%E2%9D%A4%EF%B8%8F-open%20source%20saturday-F64060.svg)](https://www.meetup.com/it-IT/Open-Source-Saturday-Milano/)

# PdfColorChanger

This program permit you to change the color inside a pdf.
This is particularly helpful when you have to change colors to iOS icons.  
The program will scan the input directory searching for all the pdf file and create a new pdf in the output directory with the new colors

# How To Use
  1. Create a mapping file. This text file is congaing the pair old color/new color.
       the color must be expressed as RGB in hex format, and separated by a space.
       for example to convert red to blue and black to  yellow your file will contains:
       ```  
       FF0000 0000FF
       000000 00FFFF
       ```
       
  2. run the program with:
        ```
            java -jar PdfColor_v1.0.0.jar inputDir mappingFile outDir
        ```
     **Note:** the output dir must be already present
     

## Limitation
The program will change the colors only to the content of the first pdf page



## License

Copyright 2020 Giovanni Visentini

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE

## Thanks
This program was developed during a [Open Source Saturday](https://www.meetup.com/it-IT/Open-Source-Saturday-Milano/)