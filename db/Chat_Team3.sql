USE [master]
GO
/****** Object:  Database [Chat_Team3]    Script Date: 11/29/2021 10:26:02 AM ******/
CREATE DATABASE [Chat_Team3]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Chat_Team3', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Chat_Team3.mdf' , SIZE = 139264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Chat_Team3_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Chat_Team3_log.ldf' , SIZE = 139264KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Chat_Team3] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Chat_Team3].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Chat_Team3] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Chat_Team3] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Chat_Team3] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Chat_Team3] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Chat_Team3] SET ARITHABORT OFF 
GO
ALTER DATABASE [Chat_Team3] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Chat_Team3] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Chat_Team3] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Chat_Team3] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Chat_Team3] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Chat_Team3] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Chat_Team3] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Chat_Team3] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Chat_Team3] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Chat_Team3] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Chat_Team3] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Chat_Team3] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Chat_Team3] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Chat_Team3] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Chat_Team3] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Chat_Team3] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Chat_Team3] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Chat_Team3] SET RECOVERY FULL 
GO
ALTER DATABASE [Chat_Team3] SET  MULTI_USER 
GO
ALTER DATABASE [Chat_Team3] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Chat_Team3] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Chat_Team3] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Chat_Team3] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Chat_Team3] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Chat_Team3] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Chat_Team3', N'ON'
GO
ALTER DATABASE [Chat_Team3] SET QUERY_STORE = OFF
GO
USE [Chat_Team3]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](250) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Contents]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Contents](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Content] [varbinary](max) NULL,
	[ID_Message] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Friendship]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Friendship](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[User_name] [nvarchar](50) NULL,
	[Friend_user_name] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Members]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Members](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ID_Room] [int] NULL,
	[User_name] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Messages]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Messages](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Message] [nvarchar](max) NULL,
	[ID_Category] [int] NULL,
	[ID_Member] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Rooms]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rooms](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](250) NULL,
	[isGroup] [bit] NULL,
	[avatar] [varbinary](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[User_name] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](20) NULL,
	[NickName] [nvarchar](50) NULL,
	[Avatar] [varbinary](max) NULL,
	[Gender] [bit] NULL,
	[Birthday] [date] NULL,
	[Email] [nvarchar](250) NULL,
	[Addres] [nvarchar](max) NULL,
	[Role] [bit] NULL,
	[Ip] [nvarchar](50) NULL,
	[Time_off] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[User_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Rooms] ADD  DEFAULT ((0)) FOR [isGroup]
GO
ALTER TABLE [dbo].[Contents]  WITH CHECK ADD  CONSTRAINT [FK__Contents__ID_Mes__31EC6D26] FOREIGN KEY([ID_Message])
REFERENCES [dbo].[Messages] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Contents] CHECK CONSTRAINT [FK__Contents__ID_Mes__31EC6D26]
GO
ALTER TABLE [dbo].[Friendship]  WITH CHECK ADD FOREIGN KEY([Friend_user_name])
REFERENCES [dbo].[Users] ([User_name])
GO
ALTER TABLE [dbo].[Friendship]  WITH CHECK ADD FOREIGN KEY([User_name])
REFERENCES [dbo].[Users] ([User_name])
GO
ALTER TABLE [dbo].[Members]  WITH CHECK ADD  CONSTRAINT [FK__Members__ID_Room__2A4B4B5E] FOREIGN KEY([ID_Room])
REFERENCES [dbo].[Rooms] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Members] CHECK CONSTRAINT [FK__Members__ID_Room__2A4B4B5E]
GO
ALTER TABLE [dbo].[Members]  WITH CHECK ADD  CONSTRAINT [FK__Members__User_na__2B3F6F97] FOREIGN KEY([User_name])
REFERENCES [dbo].[Users] ([User_name])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Members] CHECK CONSTRAINT [FK__Members__User_na__2B3F6F97]
GO
ALTER TABLE [dbo].[Messages]  WITH CHECK ADD  CONSTRAINT [FK__Messages__ID_Cat__2E1BDC42] FOREIGN KEY([ID_Category])
REFERENCES [dbo].[Categories] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Messages] CHECK CONSTRAINT [FK__Messages__ID_Cat__2E1BDC42]
GO
ALTER TABLE [dbo].[Messages]  WITH CHECK ADD  CONSTRAINT [FK__Messages__ID_Mem__2F10007B] FOREIGN KEY([ID_Member])
REFERENCES [dbo].[Members] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Messages] CHECK CONSTRAINT [FK__Messages__ID_Mem__2F10007B]
GO
/****** Object:  StoredProcedure [dbo].[add_friend]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[add_friend] @name varchar(50), @id_room int
as
begin
select members.id_room, users.NickName, users.avatar, a.message
from members 
inner join messages on members.ID = messages.ID_Member
inner join (select members.id_room, messages.message
from messages inner join (select max(messages.id) as id, members.id_room 
from messages inner join members on members.id = messages.id_member
group by members.id_room) as d on d.id = messages.id
inner join members on messages.id_member = members.id
group by members.id_room, messages.message) as a
on a.id_room = members.id_room
inner join users on users.User_name = members.User_name
where members.id_room in (select members.id_room from members
inner join users on users.user_name = members.user_name
where users.user_name = @name)
and users.user_name != @name
and members.id_room = @id_room
end;
GO
/****** Object:  StoredProcedure [dbo].[add_group]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[add_group] @id_room int
as
begin
select top 1 members.id_room, rooms.name, rooms.avatar, b.Message
from rooms inner join members on members.id_room = rooms.id
inner join (select members.id_room, messages.message from messages
inner join members on members.id = messages.id_member
inner join (select max(messages.id) as d, members.id_room from messages inner join members on members.id = messages.id_member
group by members.id_room
having members.id_room = @id_room) as a on a.ID_Room = members.id_room) as b
on b.ID_Room = members.id_room
end;
GO
/****** Object:  StoredProcedure [dbo].[all_group]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[all_group] @name varchar(50)
as
begin
select distinct rooms.id, rooms.name, rooms.avatar, a.message
from rooms
inner join members on rooms.id = members.id_room
inner join (select members.id_room, messages.message
from messages inner join (select max(messages.id)as id, members.id_room 
from messages inner join members on members.id = messages.id_member
group by members.id_room) as d on d.id = messages.id
inner join members on messages.id_member = members.id
group by members.id_room, messages.message) as a
on a.id_room = members.id_room
inner join users on users.User_name = members.User_name
where members.id_room in (select members.id_room from members
inner join users on users.user_name = members.user_name
where users.user_name = @name)
and users.user_name != @name
and rooms.isGroup = 1
end;
GO
/****** Object:  StoredProcedure [dbo].[all_room]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[all_room] @name varchar(50)
as
begin
select distinct users.NickName, rooms.id,  users.avatar, a.message
from members 
inner join rooms on rooms.id = members.id_room
inner join messages on members.ID = messages.ID_Member
inner join (select members.id_room, messages.message
from messages inner join (select max(messages.id)as id, members.id_room 
from messages inner join members on members.id = messages.id_member
group by members.id_room) as d on d.id = messages.id
inner join members on messages.id_member = members.id
group by members.id_room, messages.message) as a
on a.id_room = members.id_room
inner join users on users.User_name = members.User_name
where members.id_room in (select members.id_room from members
inner join users on users.user_name = members.user_name
where users.user_name = @name)
and users.user_name != @name
and rooms.isGroup = 0
end;
GO
/****** Object:  StoredProcedure [dbo].[getAge]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[getAge]
as
begin
select max(year(GETDATE()) - year(users.Birthday)) as max, avg(year(GETDATE()) - year(users.Birthday)) as avg, min(year(GETDATE()) - year(users.Birthday)) as min from users;
end;
GO
/****** Object:  StoredProcedure [dbo].[getNumberMessage]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[getNumberMessage]
as
begin
select  count(messages.id) as tinNhan, users.user_name from users
inner join members on members.user_name = users.user_name
inner join messages on messages.id_member = members.id
group by users.user_name
end;
GO
/****** Object:  StoredProcedure [dbo].[peopleInCountry]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[peopleInCountry]
as
begin
select count(user_name) as soluong, addres from users group by addres having addres is not null
end;
GO
/****** Object:  StoredProcedure [dbo].[whoChatLeast]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[whoChatLeast]
as
begin
select top 1 count(messages.id) as tinNhan, users.user_name from users
inner join members on members.user_name = users.user_name
inner join messages on messages.id_member = members.id
group by users.user_name
order by tinNhan asc;
end;
GO
/****** Object:  StoredProcedure [dbo].[whoChatMost]    Script Date: 11/29/2021 10:26:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[whoChatMost]
as 
begin
select top 1 count(messages.id) as tinNhan, users.user_name from users
inner join members on members.user_name = users.user_name
inner join messages on messages.id_member = members.id
group by users.user_name
order by tinNhan desc;
end;
GO
USE [master]
GO
ALTER DATABASE [Chat_Team3] SET  READ_WRITE 
GO
