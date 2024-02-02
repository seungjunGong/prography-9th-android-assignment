from pathlib import Path
import os
import shutil
import re

def check(file):
    pattern = '[^a-z0-9_]'
    if re.findall(pattern, new_file):
        print("리소스 이름에는 소문자, 숫자, _만 들어갈 수 있습니다")
        exit(0)

new_file = input()
check(new_file)
new_file += '.png'

work_dir = Path(__file__).parent
src_dir = Path(__file__).parent.parent

mdpi_dir = os.path.join(src_dir, 'app/src/main/res/drawable')
hdpi_dir = os.path.join(src_dir, 'app/src/main/res/drawable-hdpi')
xhdpi_dir = os.path.join(src_dir, 'app/src/main/res/drawable-xhdpi')
xxhdpi_dir = os.path.join(src_dir, 'app/src/main/res/drawable-xxhdpi')
xxxhdpi_dir = os.path.join(src_dir, 'app/src/main/res/drawable-xxxhdpi')

def create_directory(dir_list):
    for dir in dir_list:
        try:
            if not os.path.exists(dir):
                os.makedirs(dir)
        except OSError:
            print("오류: 새 디렉토리를 만드는데 실패했습니다")


def work():
    for _, _, files in os.walk(work_dir):
        for file in files:
            file_name, file_extension = os.path.splitext(file)
            if file_extension == '.png':
                if '@1.5x' in file_name:
                    shutil.copy(os.path.join(work_dir, file), os.path.join(hdpi_dir, new_file))
                elif '@2x' in file_name:
                    shutil.copy(os.path.join(work_dir, file), os.path.join(xhdpi_dir, new_file))
                elif '@3x' in file_name:
                    shutil.copy(os.path.join(work_dir, file), os.path.join(xxhdpi_dir, new_file))
                elif '@4x' in file_name:
                    shutil.copy(os.path.join(work_dir, file), os.path.join(xxxhdpi_dir, new_file))
                else:
                    shutil.copy(os.path.join(work_dir, file), os.path.join(mdpi_dir, new_file))

def delete_all():
    for _, _, files in os.walk(work_dir):
        for file in files:
            _, file_extension = os.path.splitext(file)
            if file_extension == '.png':
                os.remove(os.path.join(work_dir, file))

create_directory([mdpi_dir, hdpi_dir, xhdpi_dir, xxhdpi_dir, xxxhdpi_dir])
work()
delete_all()